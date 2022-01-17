package com.flame.component.kakfa.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author king
 * @date 2022年01月14日 14:49
 * @DOTO:
 */
@Slf4j
public class DefaultRetryHandler implements RetryHandler{
    private static Map<String,Integer> counter = new ConcurrentHashMap<>();
    @Value("${spring.kafka.retry-times:3}")
    private Integer retryTimes;

    @Override
    public void retry(KafkaConsumer consumer, ConsumerRecord record) {
        String topic = record.topic();
        int partition = record.partition();
        long offset = record.offset();
        log.warn("[kafka] occur error when resolve message,topic: {},partition: {}, offset: {}",topic,partition,offset);
        if (check(record)) {
            consumer.seek(new TopicPartition(topic, partition), offset);
        } else {
            Object value = record.value();
            log.warn("skip current error,offset: {},data: {}",offset,value);
            consumer.seek(new TopicPartition(topic, partition), offset + 1);
        }
    }

    private boolean check(ConsumerRecord record) {
        String topic = record.topic();
        int partition = record.partition();
        long offset = record.offset();
        Object value = record.value();
        String key = record.topic() + "_" + record.partition() + "_" + record.offset();
        Integer count = counter.get(key);
        if (count == null) {
            count = 0;
        }
        if (count >= retryTimes) {
            log.warn("[kafka] great than retry times,max times: {},topic: {},partition: {}, offset: {},message: {}",retryTimes,topic,partition,offset,value);
            counter.remove(key);
            return false;
        }
        count++;
        counter.put(key,count);
        log.warn("[kafka] seek message,current times: {},max times: {}",count,retryTimes);
        return true;
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    public void clear() {
        log.debug("[kafka] clear counter");
        counter.clear();
    }
}
