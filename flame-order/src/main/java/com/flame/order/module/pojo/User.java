package com.flame.order.module.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.flame.component.mybatisplus.entity.CommonEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 
 * 
 * @author king
 * @date 2022-02-10 09:20:02
 */
@ApiModel(description = "用户")
@Data
@TableName("FLAME_USER")
public class User extends CommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

//         @TableId
//         @ApiModelProperty(value = "主键")
//		 private String id;

         @ApiModelProperty(value = "用户名")
		 private String userName;

//         @ApiModelProperty(value = "")
//		 private Integer deleteFlag;
//
//         @ApiModelProperty(value = "")
//		 private Date createTime;
//
//         @ApiModelProperty(value = "")
//		 private Date updateTime;

}
