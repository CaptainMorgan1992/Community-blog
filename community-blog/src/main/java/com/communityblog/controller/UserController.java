package com.communityblog.controller;
import com.communityblog.JWT.JwtTokenProvider;
import com.communityblog.dto.LoginDto;
import com.communityblog.dto.SignUpDto;
import com.communityblog.repository.RoleRepository;
import com.communityblog.repository.UserRepository;
import com.communityblog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        String token = userService.authenticateUser(loginDto);
        return new ResponseEntity<>("User login successfully!..." + token, HttpStatus.OK);
    }


    @PostMapping("/logout")
     public ResponseEntity<String> logoutUser( @RequestHeader("Authorization") String token) {
        jwtTokenProvider.invalidateToken(token);
        return new ResponseEntity<>("User logout successfully!...", HttpStatus.OK);
    }


    @GetMapping("/create-blogpost")
    public ResponseEntity<String> hello(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {

            return new ResponseEntity<>("You have to login first!", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("You can create a blogpost now!", HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpDto signUpDto){
            return userService.registerUser(signUpDto);

    }
}
