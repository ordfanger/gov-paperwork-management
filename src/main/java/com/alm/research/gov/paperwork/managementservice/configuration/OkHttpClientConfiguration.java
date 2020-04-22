package com.alm.research.gov.paperwork.managementservice.configuration;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OkHttpClientConfiguration {

    private final OkHttpClientFactory factory;

    @Bean
    public OkHttpClient getOkHttpClient() {
        return factory.createBuilder(false).build();
    }
}
