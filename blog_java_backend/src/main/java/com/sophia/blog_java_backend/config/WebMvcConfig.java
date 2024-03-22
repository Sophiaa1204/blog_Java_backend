package com.sophia.blog_java_backend.config;

import com.mysql.cj.log.Log;
import com.sophia.blog_java_backend.controller.interceptor.AlphaInterceptor;
import com.sophia.blog_java_backend.controller.interceptor.LoginRequiredInterceptor;
import com.sophia.blog_java_backend.controller.interceptor.LoginTickerInterceptor;
import com.sophia.blog_java_backend.controller.interceptor.MessageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AlphaInterceptor alphaInterceptor;

    @Autowired
    private LoginTickerInterceptor loginTickerInterceptor;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Autowired
    private MessageInterceptor messageInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(alphaInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.png", "/**/*.jpeg")
                .addPathPatterns("/register","/login");

        registry.addInterceptor(loginTickerInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.png", "/**/*.jpeg");

        registry.addInterceptor(loginRequiredInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.png", "/**/*.jpeg");

        registry.addInterceptor(messageInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.png", "/**/*.jpeg");
    }


}
