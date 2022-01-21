package com.flame.component.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author king
 * @date 2022年01月21日 11:13
 * @DOTO: 分页插件
 */
@Data
public class CommonEntity implements Serializable {

    public static Integer STATUS_NORMAL = 1;

    public static Integer STATUS_DELETE = 0;

    @ApiModelProperty(value = "id")
    @TableId
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "状态")
    @TableLogic
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer delete;

}
