package com.alm.research.gov.paperwork.managementservice.okta.access;

import lombok.Data;

@Data
public class TokenRequestModel {
    private String username;
    private String password;
}
