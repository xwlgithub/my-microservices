package com.itxwl.shiroserver.exception;

import lombok.Data;

@Data
public class MyException extends RuntimeException{
    private ExceptionEnum exceptionEnum;
    private String message;
    public MyException(ExceptionEnum em){
        this.exceptionEnum=em;
    }
}
