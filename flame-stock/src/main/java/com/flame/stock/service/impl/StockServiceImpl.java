package com.flame.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flame.stock.mapper.StockMapper;
import com.flame.stock.module.pojo.Stock;
import com.flame.stock.service.StockService;
import io.seata.core.context.RootContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author king
 * @Description:
 * @date 2022-02-11 09:31:43
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

    @Transactional
    @Override
    public boolean updateStock(Stock stock) {
        System.out.println("事务ID："+ RootContext.getXID());
        UpdateWrapper<Stock> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(Stock::getStock, stock.getStock()).eq(Stock::getId, stock.getId());
        return update(updateWrapper);
    }

}