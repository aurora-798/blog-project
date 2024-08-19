package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Config配置类解决跨域问题
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {

        //1. 创建config对象
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("https://changge.plus");
        config.addAllowedOrigin("https://101.42.240.114");
        // 放行哪些原始域
        config.addAllowedOrigin("*");
        // 放行哪些请求方式
        config.addAllowedMethod("*");
        // 放行哪些原始请求头部信息
        config.addAllowedHeader("*");
        //2. 添加映射路径,需要创建source对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //将config对象放入source对象中
        source.registerCorsConfiguration("/**", config);
        //3. 返回新的CorsFilter
        return new CorsFilter(source);
    }
}
