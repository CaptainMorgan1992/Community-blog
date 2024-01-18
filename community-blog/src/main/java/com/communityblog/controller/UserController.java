package com.communityblog.controller;
import com.communityblog.dto.LoginDto;
import com.communityblog.dto.SignUpDto;
import com.communityblog.model.Role;
import com.communityblog.model.User;
import com.communityblog.repository.RoleRepository;
import com.communityblog.repository.UserRepository;
import com.communityblog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController
@RequestMapping("/api")
public class UserController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        String token = userService.authenticateUser(loginDto);
        return new ResponseEntity<>("User login successfully!..." + token, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request, HttpSession session) {
        SecurityContextHolder.clearContext();
        session.removeAttribute(CsrfToken.class.getName()); // Remove CSRF token
        session.removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
        }

        return new ResponseEntity<>("User logout successfully!...", HttpStatus.OK);
    }

    @GetMapping("/create-blogpost")
    public ResponseEntity<String> hello(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("You have to login first!", HttpStatus.UNAUTHORIZED);
        }

        // You can perform additional authorization checks if needed

        return new ResponseEntity<>("You can create a blogpost now!", HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpDto signUpDto){
            return userService.registerUser(signUpDto);

    }
}
