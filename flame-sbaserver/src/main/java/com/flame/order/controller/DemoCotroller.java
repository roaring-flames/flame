package com.flame.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author king
 * @date 2022年01月13日 14:54
 * @DOTO:
 */
@RefreshScope
@RestController
@RequestMapping("demo")
public class DemoCotroller {

    @Value("${ad.a}")
    private String ad;

    @Value("${sa.a}")
    private String sa;

    @GetMapping("at")
    public String getVariablet() {
        System.out.println(sa);
        return ad;
    }
}
