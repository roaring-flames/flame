package com.flame.stock.module.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.flame.component.mybatisplus.entity.CommonEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author king
 * @date 2022-02-11 09:31:43
 */
@ApiModel(description = "库存")
@Data
@TableName("FLAME_STOCK")
public class Stock extends CommonEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "商品库存")
    private Integer stock;

}
