package de.telekom.test.bddwebapp.taxi.appconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig {

    private final CollectiveTaxiAuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(registry -> registry
                        .requestMatchers("/webjars/**", "/css/**", "/js/**", "/registration/**", "/actuator/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(configurer -> configurer
                        .loginPage("/login").permitAll()
                        .loginProcessingUrl("/perform-login")
                        .defaultSuccessUrl("/reservation").permitAll()
                        .failureForwardUrl("/login?error=true"))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Autowired
    protected void configureAuthenticationProvider(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

}