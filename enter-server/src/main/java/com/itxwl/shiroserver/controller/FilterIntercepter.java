//package com.itxwl.shiroserver.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//@Configuration
//public class FilterIntercepter extends WebMvcConfigurationSupport {
//    @Autowired
//    private WebConmfig webConfig;
//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(webConfig).addPathPatterns("/**").excludePathPatterns("/user/Login","/user/findByNamePermission");
//    }
//}
