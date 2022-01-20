package com.flame.stock;

import com.flame.common.config.CorsConfig;
import com.flame.common.swagger.Knife4jConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@EnableDiscoveryClient
@SpringBootApplication
@Import({Knife4jConfig.class, CorsConfig.class})
public class FlameStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlameStockApplication.class, args);
    }

}
