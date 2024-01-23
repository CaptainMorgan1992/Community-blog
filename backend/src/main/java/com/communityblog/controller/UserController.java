package com.communityblog.controller;

import com.communityblog.dto.LoginDto;
import com.communityblog.dto.SignUpDto;
import com.communityblog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        userService.authenticateUser(loginDto);
        return new ResponseEntity<>("Successfully logged in!", HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request, HttpSession session) {
        return new ResponseEntity<>("Logout successful!", HttpStatus.OK);
    }

    //With a Global Exception Handler
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpDto signUpDto) {
        return userService.registerUser(signUpDto);
    }
}
