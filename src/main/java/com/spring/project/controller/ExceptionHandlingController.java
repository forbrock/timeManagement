package com.spring.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlingController {
    public static final String DEFAULT_ERROR_VIEW = "error";

    // TODO: don't forget remove exception message
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", e.getMessage());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}
