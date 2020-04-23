package com.alm.research.gov.paperwork.managementservice.controllers;

import com.alm.research.gov.paperwork.managementservice.okta.access.TokenRequestModel;
import com.alm.research.gov.paperwork.managementservice.okta.access.TokenResponseModel;
import com.alm.research.gov.paperwork.managementservice.okta.access.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(path = "/v1")
@RequiredArgsConstructor
public class ManagementController {

    private final TokenService tokenService;

    @PostMapping(
            value = "/token",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TokenResponseModel> getToken(@Valid @RequestBody TokenRequestModel creds) throws IOException {
        return ResponseEntity.ok(tokenService.getAccessToken(creds));
    }
}
