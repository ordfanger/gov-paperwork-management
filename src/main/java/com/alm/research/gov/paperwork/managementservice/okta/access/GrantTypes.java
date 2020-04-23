package com.alm.research.gov.paperwork.managementservice.okta.access;

public enum GrantTypes {
    PASSWORD("password"),
    REFRESH_TOKEN("refresh_token");
    public final String value;

    GrantTypes(String value) {
        this.value = value;
    }
}