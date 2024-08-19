package com.example.config;

import com.example.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 在此拦截请求
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    //添加自定义拦截器，开启校验规则
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())   //将该Bean注册到拦截器列表当中
                .addPathPatterns("/**")     //默认拦截所有的路径
                .excludePathPatterns("/searchApi/login")
                .excludePathPatterns("/searchApi/post/page")
                .excludePathPatterns("/searchApi/post/getPost")
                .excludePathPatterns("/searchApi/common/active/vo");     //除了登录页外不拦截
        super.addInterceptors(registry);
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

}