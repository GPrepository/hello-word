package com.education.admin.api.config;

import com.education.common.interceptor.ParamsValidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/5/10 10:04
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private ParamsValidateInterceptor paramsValidateInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(paramsValidateInterceptor);
    }
}
