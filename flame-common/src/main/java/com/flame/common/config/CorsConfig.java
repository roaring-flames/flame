package com.flame.common.config;

import com.flame.common.filter.SimpleCorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author king
 * @date 2022年01月20日 11:18
 * @DOTO:解决跨域
 */
public class CorsConfig {
    @Bean
    public FilterRegistrationBean<SimpleCorsFilter> simpleCorsFilter() {
        FilterRegistrationBean<SimpleCorsFilter> tokenBean = new FilterRegistrationBean<SimpleCorsFilter>();
        SimpleCorsFilter filter = new SimpleCorsFilter();
        tokenBean.setFilter(filter);
        tokenBean.setOrder(1);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        tokenBean.setUrlPatterns(urlPatterns);
        return tokenBean;
    }
}
