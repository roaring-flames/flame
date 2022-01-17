package com.flame.component.kakfa.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * 增加重试机制
 */
public interface RetryHandler {
    void retry(KafkaConsumer consumer, ConsumerRecord record);
}
