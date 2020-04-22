package com.alm.research.gov.paperwork.managementservice.okta.access;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final OkHttpClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TokenResponseModel getAccessToken(TokenRequestModel creds) throws IOException {

        RequestBody body = new FormBody.Builder()
                .add("grant_type", "password")
                .add("username", creds.getUsername())
                .add("password", creds.getPassword())
                .add("scope", "openid email offline_access")
                .build();

        Request request = new Request.Builder()
                .url("https://dev-407670.okta.com/oauth2/default/v1/token")
                .addHeader("Authorization", "Basic MG9hYTNnNWIwNzV4Y1dhWkY0eDY6MTJERXo0b24yWVlvQkUyQ05vN0ptWEV6YndvaW1XNEs5VnJWVWFWdg==")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            return objectMapper.readValue(response.body().bytes(), TokenResponseModel.class);
        }
    }
}
