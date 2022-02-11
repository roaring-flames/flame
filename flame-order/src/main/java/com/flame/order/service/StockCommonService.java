package com.flame.order.service;

import com.flame.common.api.stock.dto.StockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author king
 * @date 2022年02月11日 15:04
 * @DOTO:
 */
@FeignClient(name = "flame-stock")
public interface StockCommonService {

    @PostMapping(value="/stock/update")
//    @RequestMapping(value = "/stock/update",method = RequestMethod.POST)
    boolean decrease(@RequestBody StockDTO stockDTO);
}
