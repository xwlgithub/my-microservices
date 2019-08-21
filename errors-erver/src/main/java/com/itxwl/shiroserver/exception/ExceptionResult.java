package com.itxwl.shiroserver.exception;

import lombok.Data;

@Data
public class ExceptionResult {
    private int status;
    private String messages;
    private Long times;

    public ExceptionResult(ExceptionEnum exceptionEnum) {
        this.status=exceptionEnum.getStatus();
        this.messages=exceptionEnum.getMessage();
        this.times=System.currentTimeMillis();
    }
}
