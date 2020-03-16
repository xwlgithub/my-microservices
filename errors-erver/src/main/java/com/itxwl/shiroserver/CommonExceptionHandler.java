package com.itxwl.shiroserver;

import com.itxwl.shiroserver.exception.ExceptionEnum;
import com.itxwl.shiroserver.exception.ExceptionResult;
import com.itxwl.shiroserver.exception.MyException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class  CommonExceptionHandler {
    //捕捉运行时异常
    @ExceptionHandler(value = MyException.class)
    public ResponseEntity<ExceptionResult> throwModelShowException(MyException rn){
        System.out.println("捕捉的运行时异常");
        return ResponseEntity.status(rn.getExceptionEnum().getStatus()).body(new ExceptionResult(rn.getExceptionEnum()));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ExceptionResult> throwAuthenticationException(MyException rn){
        return ResponseEntity.status(rn.getExceptionEnum().getStatus()).body(new ExceptionResult(rn.getExceptionEnum()));
    }

    @ExceptionHandler(value = AuthorizationException.class)
    public ResponseEntity<ExceptionResult> throwAuthorizationException(AuthorizationException rn){
        return ResponseEntity.status(401).body(new ExceptionResult(ExceptionEnum.AUTH_NO_EVERYS));
    }
}
