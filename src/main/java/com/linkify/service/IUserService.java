package com.linkify.service;

import com.linkify.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    void save(User user);
    User findByUsername(String username);
    User findByEmail(String email);
}