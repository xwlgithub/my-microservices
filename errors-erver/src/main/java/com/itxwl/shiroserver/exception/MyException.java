package com.itxwl.shiroserver.exception;

import lombok.Data;

@Data
public class MyException extends RuntimeException{
    private ExceptionEnum exceptionEnum;
    public MyException(ExceptionEnum exceptionEnum){
        this.exceptionEnum=exceptionEnum;
    }

}
