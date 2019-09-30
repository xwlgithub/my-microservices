//package com.itxwl.shiroserver.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//public class SystemConfig extends WebMvcConfigurationSupport {
//    @Autowired
//    private JwtInterceptor jwtInterceptor;
//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//        //添加拦截器
//        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
//                .excludePathPatterns("/sys/login","sss");
//    }
//}
