package com.example.demo.adapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.demo.interceptor.MyHandlerInterceptor;

/** 
* create by 張風武 
* 2017/09/04 15:03:23 
*/
@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    
    /**
     * 添加静态资源目录
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 访问路径：http://localhost:8080/down/**.apk                使用外部目录,前缀：file
        registry.addResourceHandler("/down/*").addResourceLocations("file:D:/down_path/");
        // 如果我们将/down/* 修改为 /* 与默认的相同时，则会覆盖系统的配置，可以多次使用 addResourceLocations 添加目录，优先级先添加的高于后添加的。
//        registry.addResourceHandler("/*").addResourceLocations("file:D:/down_path/");
        super.addResourceHandlers(registry);
    }
    
    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new MyHandlerInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
