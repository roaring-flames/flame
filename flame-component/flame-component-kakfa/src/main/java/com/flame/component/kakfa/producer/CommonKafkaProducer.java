package com.flame.component.kakfa.producer;

import com.alibaba.fastjson.JSONObject;
import com.flame.component.kakfa.KafkaTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author king
 * @date 2022年01月14日 15:42
 * @DOTO:投递消息的
 */
@Slf4j
public abstract class CommonKafkaProducer <V> implements KafkaTopic {

    @Autowired
    private KafkaTemplate<String,String> kaKafkaTemplate;

    public void sendMessage(V message) {
        String topic = getTopic();
        log.info("send message to kafka with topic: {}",topic);
        kaKafkaTemplate.send(topic, JSONObject.toJSONString(message));
    }

    public void sendMessage(String key,V message) {
        kaKafkaTemplate.send(getTopic(),key,JSONObject.toJSONString(message));
    }

    public void sendMessage(int partition,V message) {
        kaKafkaTemplate.send(getTopic(),partition,"",JSONObject.toJSONString(message));
    }

}
