package com.alm.research.gov.paperwork.managementservice.okta.access;

import com.alm.research.gov.paperwork.managementservice.exceptions.LoginException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    private final OkHttpClient client;
    private final Environment environment;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${gov.paperwork.okta-url}")
    private String oktaBaseUrl;

    private final String basicToken;

    TokenService(OkHttpClient client, Environment environment) {
        this.client = client;
        this.environment = environment;

        this.basicToken = buildBasicToken();
    }

    public TokenResponseModel getAccessToken(TokenRequestModel creds) throws IOException {
        Map<String, String> params = new HashMap<>();
        String grantType = creds.getGrantType();

        params.put("grant_type", grantType);

        if (GrantTypes.password.value.equals(grantType)) {
            params.put("username", creds.getUsername());
            params.put("password", creds.getPassword());
        }

        if (GrantTypes.refresh_token.value.equals(grantType)) {
            params.put("refresh_token", creds.getRefreshToken());
        }

        return makeCall(params);
    }

    private TokenResponseModel makeCall(Map<String, String> params) throws IOException {
        FormBody.Builder builder = new FormBody.Builder()
                .add("scope", "openid profile email offline_access");

        params.forEach(builder::add);

        RequestBody body = builder.build();

        Request request = new Request.Builder()
                .url(oktaBaseUrl + "oauth2/default/v1/token")
                .addHeader("Authorization", "Basic " + basicToken)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new LoginException(response.code(), response.body().string());
            }

            return objectMapper.readValue(response.body().bytes(), TokenResponseModel.class);
        }
    }

    private String buildBasicToken() {
        String base64TokenString = environment.getProperty("gov.paperwork.okta-client-id")
                + ":" + environment.getProperty("gov.paperwork.okta-client-secret");

        return Base64.getEncoder().encodeToString(base64TokenString.getBytes());
    }
}
