package com.alm.research.gov.paperwork.managementservice.okta.access;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponseModel {
    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty("access_token")
    private String accessToken;

    private String scope;

    @JsonProperty("refresh_token")
    private String refreshToken;
}
