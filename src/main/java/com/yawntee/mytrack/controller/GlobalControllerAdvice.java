package com.yawntee.mytrack.controller;

import com.google.common.collect.Iterables;
import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.pojo.Resp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.HandlerMapping;

import java.util.stream.Collectors;


@Slf4j
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
    public void handler(@AuthenticationPrincipal User user, HttpServletRequest request) {
        log.warn(
                "用户[{}]试图访问未授权接口：{} {}",
                user.getUsername(),
                request.getMethod(),
                request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE)
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Resp<?> handler(ConstraintViolationException e) {
        return Resp.fail(
                e.getConstraintViolations()
                        .stream()
                        .map(v -> Iterables.getLast(v.getPropertyPath()) + v.getMessage())
                        .collect(Collectors.joining(";"))
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Resp<?> handler(MethodArgumentNotValidException e) {
        return Resp.fail(
                e.getFieldErrors()
                        .stream()
                        .map(v -> v.getField() + v.getDefaultMessage())
                        .collect(Collectors.joining(";"))
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Resp<?> handler(MethodArgumentTypeMismatchException e) {
        return Resp.fail(String.format("参数[%s]类型不匹配", e.getName()));
    }

}
