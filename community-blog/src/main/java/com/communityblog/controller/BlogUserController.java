package com.communityblog.controller;

import com.communityblog.dto.RegisterSuccess;
import com.communityblog.service.BlogUserService;
import jakarta.validation.Valid;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BlogUserController {

    private final BlogUserService blogUserService;

    @Builder
    private record UserDTO(String username, String password, String role, String email ){};

    @Autowired
    public BlogUserController(BlogUserService blogUserService) {
        this.blogUserService = blogUserService;
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterSuccess> registerUser(@Valid @RequestBody UserDTO userDTO) {
        var result = blogUserService.register(userDTO.username(), userDTO.password(), userDTO.email());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/success")
    public String success(){
        return "Register successful!";
    }

    @GetMapping("/hello")
    public String getMessage(){
        return "Hello Lina!";
    }


}
