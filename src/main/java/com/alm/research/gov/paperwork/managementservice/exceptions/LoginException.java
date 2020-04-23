package com.alm.research.gov.paperwork.managementservice.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginException extends IOException {
    private int code;
    private String error;

    public LoginException(int code, String error) {
        super(error);

        this.code = code;
        this.error = error;
    }
}
