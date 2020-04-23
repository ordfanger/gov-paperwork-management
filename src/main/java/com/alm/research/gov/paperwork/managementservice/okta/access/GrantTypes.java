package com.alm.research.gov.paperwork.managementservice.okta.access;

public enum GrantTypes {
    password("password"),
    refresh_token("refresh_token");
    public final String value;

    GrantTypes(String value) {
        this.value = value;
    }
}