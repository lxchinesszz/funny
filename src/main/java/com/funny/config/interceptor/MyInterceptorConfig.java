package com.funny.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Package: foxlife.base.config
 * @Description: 配置拦截器
 * @author: liuxin
 * @date: 17/2/22 上午11:04
 */
@Configuration
public class MyInterceptorConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthUserInterceptor()).addPathPatterns("/**");
    }
}
