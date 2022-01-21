package com.flame.component.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.flame.component.mybatisplus.handler.MyBatisMetaObjectHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author king
 * @date 2022年01月21日 11:11
 * @DOTO: 配置分页插件
 */
@Configuration
public class MybatisPlusConfig {

    @Value(value = "${mybatisplus.workerId}")
    private long workerId;

    @Value(value = "${mybatisplus.datacenterId}")
    private long datacenterId;

    /**
     * 旧版本分页插件配置
     */
  /*  @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }*/


    /**
     * Mybatis-plus3.5.0版本配置 以前的版本还使用上面的旧配置
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题
     * 注意DbType.ORACLE改为自己使用的数据库类型，否则分页也不生效
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseGeneratedShortKey(false);
    }

    /**
     * 使用mybatisplus的自动填充功能
     * @return
     */
    @Bean
    public MyBatisMetaObjectHandler myMetaObjectHandler() {
        MyBatisMetaObjectHandler myMetaObjectHandler=new MyBatisMetaObjectHandler(workerId,datacenterId);
        return myMetaObjectHandler;
    }
}


