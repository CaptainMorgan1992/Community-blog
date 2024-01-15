package com.communityblog.controller;

import com.communityblog.dto.RegisterSuccess;
import com.communityblog.model.Role;
import com.communityblog.service.BlogUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BlogUserController {

    private BlogUserService blogUserService;
    private record UserDTO(String username, String password, List<Role> role, String email ){};

    @Autowired
    public BlogUserController(BlogUserService blogUserService) {
        this.blogUserService = blogUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterSuccess> registerUser(@Valid @RequestBody UserDTO userDTO) {
        var result = blogUserService.register(userDTO.username(), userDTO.password(), userDTO.email(), userDTO.role());
        return ResponseEntity.ok().body(result);
    }

}
