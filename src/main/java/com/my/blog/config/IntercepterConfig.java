package com.my.blog.config;

import com.my.blog.intercepter.AuthenticationIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 17:36 2019/5/16
 * @Modified By:
 */
@Configuration
public class IntercepterConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationIntercepter())
                .addPathPatterns("/**");
        // 拦截所有请求，通过判断是否有 @UserLoginToken判断
    }

    @Bean
    public AuthenticationIntercepter authenticationIntercepter(){
        return new AuthenticationIntercepter();
    }
}
