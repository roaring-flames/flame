package com.flame.component.kakfa.annotation;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * @author king
 * @date 2022年01月14日 11:40
 * @DOTO:
 */
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {
    private final Consumer consumer = new Consumer();

    public Map<String,Object> buildConsumerProperties() {
        return this.consumer.buildProperties();
    }
    @Data
    public static class Consumer {
        private int sessionTimeout = 30000;
        private Duration maxPollInterval = Duration.of(1, ChronoUnit.MINUTES);
        private int pollSleepInterval = 100;
        private int pollWaitTime = 1000;

        public Consumer() {
        }

        public Map<String, Object> buildProperties() {
            Map<String,Object> properties = new HashMap<>();
            properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);//消费组失效超时时间
            properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,(int)maxPollInterval.toMillis());
            return properties;
        }
    }

    public Consumer getConsumer() {
        return consumer;
    }
}
