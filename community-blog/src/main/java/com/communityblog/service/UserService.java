package com.communityblog.service;

import com.communityblog.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public Authentication authenticateUser(LoginDto loginDto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}

