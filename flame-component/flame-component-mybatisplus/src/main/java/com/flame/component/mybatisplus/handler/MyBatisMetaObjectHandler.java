package com.flame.component.mybatisplus.handler;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.flame.component.mybatisplus.entity.CommonEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * @author king
 * @date 2022年01月21日 11:13
 * @DOTO: mybatisplus自动填充功能
 */
@Slf4j
public class MyBatisMetaObjectHandler implements MetaObjectHandler {
    private long workerId;

    private long datacenterId;

    public MyBatisMetaObjectHandler(long workerId, long datacenterId) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 添加语句自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Snowflake snowflake=new Snowflake(workerId,datacenterId);

        log.info("start insert fill ....");
        // 默认创建的id为雪花算法生成
        this.strictInsertFill(metaObject, "id", String.class, snowflake.nextIdStr()); // 起始版本 3.3.0(推荐使用)
        // 默认创建时间是今天
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date()); // 起始版本 3.3.0(推荐使用)
        // 默认更新时间是今天
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date()); // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "deleteFlag", Integer.class, CommonEntity.STATUS_NORMAL);
    }

    /**
     * 更新语句自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        //this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date()); // 起始版本 3.3.0(推荐)
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
