package com.linkify.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;

    @Test
    public void shouldCreateSecurityFilterChain() throws Exception {
        HttpSecurity http = Mockito.mock(HttpSecurity.class);
        SecurityFilterChain securityFilterChain = securityConfig.securityFilterChain(http);
        assertNotNull(securityFilterChain);
    }

}