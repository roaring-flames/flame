package com.flame.component.kakfa;

import com.alibaba.fastjson.JSONObject;
import com.flame.component.kakfa.consumer.CommonConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author king
 * @date 2022年01月14日 14:52
 * @DOTO:既可发送消息，也可以消费消息，发送和消费的是用一个topic的消息
 */
@Slf4j
public abstract class CommonMessage<V> extends CommonConsumer<V> {
    @Autowired
    private KafkaTemplate<String,String> kaKafkaTemplate;

    public void sendMessage(V message) {
        String topic = getTopic();
        log.info("send message to kafka with topic: {}",topic);
        kaKafkaTemplate.send(getTopic(), JSONObject.toJSONString(message));
    }
}
