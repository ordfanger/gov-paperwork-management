package com.alm.research.gov.paperwork.managementservice.exceptions.handlers;

import com.alm.research.gov.paperwork.managementservice.exceptions.LoginException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class LoginExceptionHandler {

    @ExceptionHandler(value = {LoginException.class, IOException.class})
    public ResponseEntity<Object> handle(LoginException e) {
        return ResponseEntity
                .status(e.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(e.getError());
    }
}
