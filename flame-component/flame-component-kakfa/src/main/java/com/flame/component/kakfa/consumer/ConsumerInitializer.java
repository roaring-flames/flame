package com.flame.component.kakfa.consumer;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author king
 * @date 2022年01月14日 14:32
 * @DOTO:
 */
@Slf4j
public class ConsumerInitializer implements InitializingBean{

    private static final String DEFAULT_GROUP_ID = "DEFAULT-GROUP-ID";

    @Autowired
    private KafkaProperties kafkaProperties;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private com.flame.component.kakfa.annotation.KafkaProperties commonKafkaProperties;

    private static List<Class<?>> commonConsumers = new ArrayList<>();

    @Autowired
    private RetryHandler retryHandler;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("[kafka] initialing kafka component ....");
        for (Class<?> commonConsumer : commonConsumers) {
            log.info("[kafka] starting init kafka component:{}", commonConsumer);
            Type[] types = ((ParameterizedType) commonConsumer.getGenericSuperclass()).getActualTypeArguments();

            Map<String, Object> properties = commonKafkaProperties.buildConsumerProperties();

            properties.putAll(kafkaProperties.buildConsumerProperties());

            CommonConsumer bean = (CommonConsumer) applicationContext.getBean(commonConsumer);
            String groupId = bean.getGroupId();
            if (StringUtils.isNotBlank(groupId)) {
                log.info("[kafka] kafka use custom group id: {}", groupId);
                properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            }
            Object gid = properties.get(ConsumerConfig.GROUP_ID_CONFIG);
            if (gid == null || "".equals(gid.toString())) {
                log.info("[kafka] use default group id: {}", DEFAULT_GROUP_ID);
                properties.put(ConsumerConfig.GROUP_ID_CONFIG, DEFAULT_GROUP_ID);
            }
            log.info("[kafka] kafka consumer topic: {}, properties: {}", bean.getTopic(), properties);
            //加载用户的配置
            bean.config(properties);

            KafkaConsumer kafkaConsumer = new KafkaConsumer(properties);

            kafkaConsumer.subscribe(Collections.singletonList(bean.getTopic()), new ConsumerRebalanceListener() {
                @Override
                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                    kafkaConsumer.commitSync();
                }
                @Override
                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                }
            });
            //使用异步线程处理注册事件
            ThreadUtil.execAsync(() -> new MessageRunner(kafkaConsumer, bean, types[0], commonKafkaProperties, properties, retryHandler), false);
            log.info("[kafka] successfully init kafka component: {}", commonConsumer);
        }
    }

    public static void addConsumer(Class<?> clazz) {
        commonConsumers.add(clazz);
    }

    @Slf4j
    private static class MessageRunner implements Runnable {

        private KafkaConsumer kafkaConsumer;
        private CommonConsumer commonConsumer;
        private Type type;
        private com.flame.component.kakfa.annotation.KafkaProperties commonKafkaProperties;
        private Map<String, Object> kafkaProperties;
        private RetryHandler retryHandler;

        public MessageRunner(KafkaConsumer kafkaConsumer, CommonConsumer commonConsumer, Type type,
                             com.flame.component.kakfa.annotation.KafkaProperties commonKafkaProperties, Map<String, Object> kafkaProperties,
                             RetryHandler retryHandler) {
            this.kafkaConsumer = kafkaConsumer;
            this.commonConsumer = commonConsumer;
            this.type = type;
            this.commonKafkaProperties = commonKafkaProperties;
            this.kafkaProperties = kafkaProperties;
            this.retryHandler = retryHandler;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    com.flame.component.kakfa.annotation.KafkaProperties.Consumer consumer = commonKafkaProperties.getConsumer();
                    Thread.sleep(consumer.getPollSleepInterval());
                    ConsumerRecords records = kafkaConsumer.poll(consumer.getPollWaitTime());
                    if (records.isEmpty()) {
                        continue;
                    }
                    ConsumerRecord record = null;
                    try {
                        for (Object consumerRecord : records) {
                            record = (ConsumerRecord) consumerRecord;
                            Object value = record.value();
                            if (value != null) {
                                commonConsumer.onMessage(JSONObject.parseObject(value.toString(), type));
                            }
                        }
                    } catch (Exception e) {
                        log.error("handle kafka message error,try again", e);
                        if (record != null) {
                            this.retryHandler.retry(kafkaConsumer, record);
                        }
                    }
                    //log.info("偏移量:" + record.offset());
                    Boolean b = (Boolean) kafkaProperties.getOrDefault(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
                    if (!b) {
                        try {
                            kafkaConsumer.commitAsync();
                        } catch (Exception e) {
                            kafkaConsumer.commitSync();
                        }
                    }
                } catch (Exception e) {
                    log.error("handle kafka message error,try again", e);
                }
            }
        }
    }
}
