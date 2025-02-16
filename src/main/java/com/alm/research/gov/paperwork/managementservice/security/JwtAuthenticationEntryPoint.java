package com.alm.research.gov.paperwork.managementservice.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final BearerTokenAuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

    private final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException reason) throws IOException {

        this.delegate.commence(request, response, reason);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if (reason.getCause() instanceof JwtValidationException) {
            JwtValidationException validationException = (JwtValidationException) reason.getCause();
            Collection<OAuth2Error> errors = validationException.getErrors();
            this.mapper.writeValue(response.getWriter(), errors);
        } else {
            Map<String, Object> message = new HashMap<>();
            message.put("timestamp", new Date());
            message.put("status", HttpStatus.UNAUTHORIZED.value());
            message.put("message", reason.getMessage());
            this.mapper.writeValue(response.getWriter(), message);
        }
    }
}
