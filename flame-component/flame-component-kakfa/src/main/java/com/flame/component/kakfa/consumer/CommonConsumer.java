package com.flame.component.kakfa.consumer;

import com.flame.component.kakfa.KafkaTopic;

import java.util.Map;

/**
 * @author king
 * @date 2022年01月14日 14:50
 * @DOTO:
 */
public abstract class CommonConsumer <V> implements KafkaTopic {

    protected abstract void onMessage(V message);
    //想实现自定义groupId,可以覆盖这个方法返回自定id
    protected String getGroupId() {
        return "";
    }
    //重试处理机制
    protected Class<? extends RetryHandler> retryHandler() {
        return DefaultRetryHandler.class;
    }
    //用户根据消费者的特点来修改配置，优先级最高，会覆盖默认配置
    //不配置时使用全局默认配置
    protected void config(Map<String,Object> properties) { }
}
