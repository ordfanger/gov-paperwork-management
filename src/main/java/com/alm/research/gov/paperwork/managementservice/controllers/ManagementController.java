package com.alm.research.gov.paperwork.managementservice.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ManagementController {

    @GetMapping(path = "/customScope")
    @PreAuthorize("hasAuthority('SCOPE_customScope')")
    public Map<String, Object> customScope(JwtAuthenticationToken authentication) {
        return authentication.getTokenAttributes();

    }

    @GetMapping(path = "/testEndpoint")
    public Map<String, Object> login(JwtAuthenticationToken authentication) {
        return authentication.getTokenAttributes();

    }

    @GetMapping("/userProfile")
    @PreAuthorize("hasAuthority('SCOPE_email')")
    public Map<String, Object> getUserDetails(JwtAuthenticationToken authentication) {
        return authentication.getTokenAttributes();
    }

}
