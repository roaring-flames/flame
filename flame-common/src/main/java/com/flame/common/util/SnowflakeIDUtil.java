package com.flame.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @author king
 * @date 2022年01月21日 11:43
 * @DOTO:生成雪花id
 */
public class SnowflakeIDUtil {

    public static Snowflake snowflake(long workerId, long datacenterId) {
        //IdUtil.createSnowflake(workerId, datacenterId);此方法有并发问题，生成同样id，不建议使用
        return IdUtil.getSnowflake(workerId, datacenterId);
    }
}
