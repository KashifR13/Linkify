package com.linkify.service;

import com.linkify.model.User;
import com.linkify.repository.UserRepository;
import com.linkify.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void shouldSaveUserSuccessfully() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        userService.save(user);

        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void shouldFindUserByUsername() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(user);

        User foundUser = userService.findByUsername(username);
        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
    }

    @Test
    public void shouldFindUserByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(user);

        User foundUser = userService.findByEmail(email);
        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
    }

    @Test
    public void shouldFailToFindUserByUsername() {
        String username = "nonExistentUser";

        when(userRepository.findByUsername(username)).thenReturn(null);

        User foundUser = userService.findByUsername(username);
        assertNull(foundUser);
    }

    @Test
    public void shouldFailToFindUserByEmail() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(null);

        User foundUser = userService.findByEmail(email);
        assertNull(foundUser);
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