package com.itxwl.enterserver.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    EXCEPTION_RUN_ERROR("服务器异常",500);
    ;
    //异常信息
    private String message;
    //状态码
    private int status;
}
