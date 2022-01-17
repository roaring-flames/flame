package com.flame.component.kakfa.config;

import com.flame.component.kakfa.annotation.KafkaProperties;
import com.flame.component.kakfa.consumer.ConsumerInitializer;
import com.flame.component.kakfa.consumer.DefaultRetryHandler;
import com.flame.component.kakfa.consumer.RetryHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author king
 * @date 2022年01月14日 11:44
 * @DOTO:
 */
@Configuration
@EnableConfigurationProperties({KafkaProperties.class})
@Import(ConsumerInitializer.class)
public class KafkaAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean({RetryHandler.class})
    public RetryHandler retryHandler() {
        return new DefaultRetryHandler();
    }

}
