package com.flame.order;

import com.flame.common.config.CorsConfig;
import com.flame.common.swagger.Knife4jConfig;
import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

//开启代理数据源
@EnableAutoDataSourceProxy
@EnableDiscoveryClient
//取消数据源自动创建
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
@ComponentScan({"com.flame.common.api","com.flame.order.service","com.flame.order.controller","com.flame.order.mapper"})
@Import({Knife4jConfig.class,CorsConfig.class})
public class FlameOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlameOrderApplication.class, args);
    }

}
