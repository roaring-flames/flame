package com.flame.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.flame.stock.module.pojo.Stock;

/**
 * @author king
 * @date 2022-02-11 09:31:43
 */
public interface StockService extends IService<Stock> {

    boolean updateStock(Stock stock);

}

