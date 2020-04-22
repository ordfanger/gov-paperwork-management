package com.alm.research.gov.paperwork.managementservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends Exception {

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public void APIExceptionHandler(Exception e) {
        log.info(e.getLocalizedMessage());
        throw new RuntimeException(e.getLocalizedMessage());
    }
}
