package com.alm.research.gov.paperwork.managementservice.okta.access;

import com.alm.research.gov.paperwork.managementservice.okta.access.validators.Conditional;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Conditional(selected = "grantType", values = {"password"}, required = {"username", "password"})
@Conditional(selected = "grantType", values = {"refresh_token"}, required = {"refreshToken"})
public class TokenRequestModel {
    private String username;
    private String password;

    @NotBlank(message = "grantType is missing in request body.")
    private String grantType;

    private String refreshToken;
}
