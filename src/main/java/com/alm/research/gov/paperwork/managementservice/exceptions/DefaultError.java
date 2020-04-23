package com.alm.research.gov.paperwork.managementservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DefaultError {
    private String message;
    private Date timestamp;
    private int statusCode;
}
