package com.alm.research.gov.paperwork.managementservice.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final BearerTokenAccessDeniedHandler delegate = new BearerTokenAccessDeniedHandler();
    private final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        this.delegate.handle(request, response, accessDeniedException);

        Map<String, Object> message = new HashMap<>();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        message.put("error", BearerTokenErrorCodes.INSUFFICIENT_SCOPE);
        message.put("timestamp", new Date());
        message.put("status", HttpStatus.FORBIDDEN.value());
        message.put("message", "The request requires higher privileges than provided by the access token.");

        this.mapper.writeValue(response.getWriter(), message);
    }
}
