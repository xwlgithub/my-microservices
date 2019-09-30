//package com.itxwl.shiroserver.controller;
//
//import com.itxwl.shiroserver.exception.ExceptionEnum;
//import com.itxwl.shiroserver.exception.MyException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//@Component
//public class WebConmfig extends HandlerInterceptorAdapter {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String username =request.getHeader("username");
//        String menus="select,sss";
//        if (username!=null){
//            if (username.equals("4")){
//                return true;
//            }else {
//                HandlerMethod handlerMethod=(HandlerMethod)handler;
//                RequestMapping mapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
//                String name = mapping.name();
//                if (!menus.contains(name)){
//                    throw  new MyException(ExceptionEnum.SHIRO_UNDEFINED);
//                }else {
//                    return true;
//                }
//            }
//        }
//        throw  new MyException(ExceptionEnum.NOT_LOGIN);
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        super.afterCompletion(request, response, handler, ex);
//    }
//
//    @Override
//    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        super.afterConcurrentHandlingStarted(request, response, handler);
//    }
//}
