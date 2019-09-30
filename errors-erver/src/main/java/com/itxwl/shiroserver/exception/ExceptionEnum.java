package com.itxwl.shiroserver.exception;


import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public enum ExceptionEnum {
    EXCEPTION_RUN_ERROR("服务器异常",500),
    AUTH_NO_EVERYS("访问受限,请求未授权",401),
    USERNAME_NO_EXISTENCE("用户名不存在",404),
    USERNAME_AND_PSD_ERROR("用户名或密码错误",500),
    SUCCESS("success",200),
    NOT_LOGIN("请登录后再访问",401),
    SHIRO_UNDEFINED("权限不足",401)
    ;
    //异常信息existence
    private String message;
    //状态码
    private int status;
}
