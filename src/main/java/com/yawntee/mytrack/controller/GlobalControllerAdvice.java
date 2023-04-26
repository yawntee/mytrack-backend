package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.pojo.Resp;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(Exception.class)
    public Resp<?> handlerException(Exception e) {
        //如果不是已知异常，返回系统异常
        e.printStackTrace();
        return Resp.fail("系统异常");
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handler(AccessDeniedException e) {
        e.printStackTrace();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Resp<?> handler(ConstraintViolationException e) {
        return Resp.fail(e.getLocalizedMessage());
    }

}
