package com.flame.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flame.stock.module.pojo.Stock;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author king
 * @date 2022-02-11 09:31:43
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {

}
