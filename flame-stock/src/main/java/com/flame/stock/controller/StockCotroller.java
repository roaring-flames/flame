package com.flame.stock.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "库存管理")
@RefreshScope
@RestController
@RequestMapping("demo")
public class StockCotroller {

    @Value("${ad.a}")
    private String ad;

    @Value("${sa.a}")
    private String sa;

    @ApiOperation(value = "查询用户",notes = "根据id查询用户")
    @GetMapping("at")
    public String getVariablet() {
        System.out.println(sa);
        return ad;
    }
}
