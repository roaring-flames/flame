package com.flame.stock.controller;

import com.flame.stock.module.pojo.Stock;
import com.flame.stock.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author king
 * @date 2022年01月13日 14:54
 * @DOTO:
 */
@Api(tags = "库存管理")
@RefreshScope
@RestController
@RequestMapping("stock")
public class StockCotroller {

    @Autowired
    private StockService stockService;

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public Boolean addStock(@RequestBody Stock record) {
        return stockService.save(record);
    }

    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public Boolean updateStock(@RequestBody Stock record) {
        return stockService.updateStock(record);
    }

    @ApiOperation(value = "查询列表")
    @GetMapping("/select")
    public List<Stock> selectStock() {
        return stockService.list();
    }

}
