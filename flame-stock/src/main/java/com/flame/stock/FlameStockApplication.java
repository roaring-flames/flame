package com.flame.stock;

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
@EnableFeignClients
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"com.flame.common.api","com.flame.stock.service","com.flame.stock.controller"})
@Import({Knife4jConfig.class, CorsConfig.class})
public class FlameStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlameStockApplication.class, args);
    }

}
