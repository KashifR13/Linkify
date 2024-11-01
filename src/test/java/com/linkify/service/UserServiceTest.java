package com.linkify.service;

import com.linkify.model.User;
import com.linkify.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void shouldLoadUserByUsernameSuccessfully() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        user.setPassword("testPassword");

        when(userRepository.findByUsername(username)).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername(username);
        assertNotNull(userDetails);
    }

    @Test
    public void shouldFailToLoadUserByUsername() {
        String username = "testUser";

        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
    }

}