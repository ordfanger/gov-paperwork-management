package com.alm.research.gov.paperwork.managementservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    // @formatter:off
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/userProfile")
                    .hasAnyAuthority("SCOPE_userProfile");

        http.authorizeRequests()
                .antMatchers("/actuator/**")
                    .permitAll()
                .anyRequest()
                    .authenticated()
                    .and()
                .oauth2ResourceServer()
                    .accessDeniedHandler(new JwtAccessDeniedHandler())
                    .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .jwt();

        http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    // @formatter:on
}


