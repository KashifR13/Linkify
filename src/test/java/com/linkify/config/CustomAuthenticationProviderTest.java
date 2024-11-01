package com.linkify.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CustomAuthenticationProviderTest {

    private CustomAuthenticationProvider customAuthenticationProvider;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userDetailsService = Mockito.mock(UserDetailsService.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        customAuthenticationProvider = new CustomAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    @Test
    public void shouldAuthenticateSuccessfully() {
        String username = "testUser";
        String password = "testPassword";
        UserDetails userDetails = Mockito.mock(UserDetails.class);

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(userDetails.getPassword()).thenReturn(password);
        when(passwordEncoder.matches(password, password)).thenReturn(true);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        assertNotNull(customAuthenticationProvider.authenticate(authenticationToken));
    }

    @Test
    public void shouldFailAuthentication() {
        String username = "testUser";
        String password = "testPassword";

        when(userDetailsService.loadUserByUsername(username)).thenReturn(null);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        assertThrows(AuthenticationException.class, () -> customAuthenticationProvider.authenticate(authenticationToken));
    }

}