package com.education.admin.api.config;

import com.education.admin.api.interceptor.AuthInterceptor;
import com.education.common.interceptor.LogInterceptor;
import com.education.common.interceptor.ParamsValidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * spring mvc系统配置类
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/5/10 10:04
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private ParamsValidateInterceptor paramsValidateInterceptor;

    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private LogInterceptor logInterceptor;

    private static final List<String> noInterceptorUrl = new ArrayList() {
        {
            add("/login");
            add("/image");
        }
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(paramsValidateInterceptor);
        registry.addInterceptor(logInterceptor);
        registry.addInterceptor(authInterceptor)
                .excludePathPatterns(noInterceptorUrl).addPathPatterns("/**");
    }
}
