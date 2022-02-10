package com.flame.order;

import com.flame.common.config.CorsConfig;
import com.flame.common.swagger.Knife4jConfig;
import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@EnableAutoDataSourceProxy //开启代理数据源
@EnableDiscoveryClient
@SpringBootApplication
@Import({Knife4jConfig.class,CorsConfig.class})
public class FlameOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlameOrderApplication.class, args);
    }

}
