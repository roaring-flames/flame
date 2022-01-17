package com.flame.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FlameOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlameOrderApplication.class, args);
    }

}
