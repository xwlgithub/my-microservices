package com.itxwl.enterserver;

import com.itxwl.enterserver.exception.ExceptionResult;
import com.itxwl.enterserver.exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {
    //捕捉运行时异常
    @ExceptionHandler(value = MyException.class)
    public ResponseEntity<ExceptionResult> throwModelShowException(MyException rn){
        return ResponseEntity.status(rn.getExceptionEnum().getStatus()).body(new ExceptionResult(rn.getExceptionEnum()));
    }
}
