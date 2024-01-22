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
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        userService.authenticateUser(loginDto);
        return new ResponseEntity<>("User login successfully!...", HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request, HttpSession session) {
        return new ResponseEntity<>("User logout successfully!...", HttpStatus.OK);
    }

    //With a Global Exception Handler
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpDto signUpDto) {
        return userService.registerUser(signUpDto);
    }

    //Without a Global Exception Handler
  /*  @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpDto signUpDto, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining("\n"));

            return ResponseEntity.badRequest().body(errorMessage);
        }

        return userService.registerUser(signUpDto);
    }*/


}
