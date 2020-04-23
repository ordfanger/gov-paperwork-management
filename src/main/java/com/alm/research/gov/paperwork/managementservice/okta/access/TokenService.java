package com.alm.research.gov.paperwork.managementservice.okta.access;

import com.alm.research.gov.paperwork.managementservice.exceptions.LoginException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

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

    private String buildBasicToken() {
        String base64TokenString = environment.getProperty("gov.paperwork.okta-client-id")
                + ":" + environment.getProperty("gov.paperwork.okta-client-secret");

       return Base64.getEncoder().encodeToString(base64TokenString.getBytes());
    }

    public TokenResponseModel getAccessToken(TokenRequestModel creds) throws IOException {

        RequestBody body = new FormBody.Builder()
                .add("grant_type", "password")
                .add("username", creds.getUsername())
                .add("password", creds.getPassword())
                .add("scope", "openid profile email offline_access")
                .build();

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
}
