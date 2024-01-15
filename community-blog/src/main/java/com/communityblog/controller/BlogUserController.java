package com.communityblog.controller;

import com.communityblog.dto.RegisterSuccess;
import com.communityblog.service.BlogUserService;
import jakarta.validation.Valid;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.ok().body(result);
    }



   /* @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
        //THIS IS JUST AN EXAMPLE CODE. NOT FOR DEVELOPER USER!
        try {
            Role roleEnum = (userDTO.role() != null) ? Role.valueOf(userDTO.role().toString()) : null;

            if (roleEnum != null) {
                var result = blogUserService.register(userDTO.username(), userDTO.password(), userDTO.email(), roleEnum.toString());
                return ResponseEntity.ok().body("Registration successful");
            } else {
                // Handle the case where the role is null
                return ResponseEntity.badRequest().body("Role cannot be null");
            }
        } catch (IllegalArgumentException e) {
            // Handle invalid role string, for example, log an error or return a specific response
            return ResponseEntity.badRequest().body("Invalid role provided");
        }
    }*/



}
