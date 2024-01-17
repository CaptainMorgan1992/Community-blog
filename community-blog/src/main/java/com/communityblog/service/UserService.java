package com.communityblog.service;

import com.communityblog.JWT.JwtTokenProvider;
import com.communityblog.dto.LoginDto;
import com.communityblog.dto.SignUpDto;
import com.communityblog.model.Role;
import com.communityblog.model.User;
import com.communityblog.repository.RoleRepository;
import com.communityblog.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpSession httpSession;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, HttpSession httpSession, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.httpSession = httpSession;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
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

    public ResponseEntity<String> registerUser(SignUpDto signUpDto) {
        // checking for username exists in a database
        if(userRepository.existsByUserName(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already exist!", HttpStatus.BAD_REQUEST);
        }
        // checking for email exists in a database
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already exist!", HttpStatus.BAD_REQUEST);
        }
        // creating user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUserName(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        Role roles = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<String>("Registration successfull!", HttpStatus.OK);
    }
}

