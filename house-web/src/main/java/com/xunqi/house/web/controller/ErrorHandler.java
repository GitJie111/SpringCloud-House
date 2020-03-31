package com.xunqi.house.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-31 10:33
 **/
@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(value = {Exception.class,RuntimeException.class})
    public String error500(HttpServletRequest request,Exception e) {
        log.error(e.getMessage(),e);
        log.error(request.getRequestURI() + "encounter 500");
        return "error/500";
    }

}
