package com.alm.research.gov.paperwork.managementservice.exceptions.handlers;

import com.alm.research.gov.paperwork.managementservice.exceptions.DefaultError;
import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
@Order()
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> APIExceptionHandler(Exception e) {
        Throwable cause = e.getCause();

        if (cause instanceof JsonParseException) {
            Map<String, Object> message = new HashMap<>();
            message.put("timestamp", new Date());
            message.put("status", HttpStatus.BAD_REQUEST.value());
            message.put("message", "Invalid body");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(message);
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new DefaultError("Server error", new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
