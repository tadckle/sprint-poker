package org.zhxie.sprinpoker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.zhxie.sprinpoker.interceptor.JwtInterceptor;

/**
 * Created by jianyang on 2019/1/9.
 */
@Configuration
public class InterceptorsConfig extends WebMvcConfigurationSupport {
    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).
                addPathPatterns("/**").
                excludePathPatterns("/**/login", "/**/regist");
    }
}
