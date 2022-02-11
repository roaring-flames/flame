package com.flame.common.api.stock.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author king
 * @date 2022年02月11日 10:32
 * @DOTO:
 */
@Data
@ApiModel(value = "修改库存入参")
public class StockDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "商品库存")
    private Integer stock;
}
