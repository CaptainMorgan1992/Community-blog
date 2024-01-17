package com.communityblog.service;

import com.communityblog.JWT.JwtTokenProvider;
import com.communityblog.dto.LoginDto;
import com.communityblog.model.User;
import com.communityblog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpSession httpSession;
    private final UserRepository userRepository;

    @Autowired
    public UserService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, HttpSession httpSession, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.httpSession = httpSession;
        this.userRepository = userRepository;
    }

    public String authenticateUser(LoginDto loginDto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUserName(loginDto.getUsername());

        // Generate JWT Token
        String token = jwtTokenProvider.generateToken(user);

        // Store token in session
        httpSession.setAttribute("JWT_TOKEN", token);

        // You may return the token or any other relevant response
        return token;
    }


    //Todo: This will belong to the Blogpost (Relational Database)
    public User getBlogUserById(Integer id){
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }



}

