package com.communityblog.service;

import com.communityblog.dto.RegisterSuccess;
import com.communityblog.exception.RegistrationFailureException;
import com.communityblog.model.BlogUser;
import com.communityblog.repository.BlogUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogUserService {
    private final BlogUserRepository blogUserRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public BlogUserService(BlogUserRepository blogUserRepository, PasswordEncoder passwordEncoder) {
        this.blogUserRepository = blogUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterSuccess register(String username, String password, String email ) {
        BlogUser newBlogUser = new BlogUser(username, password, email, "USER");
        String hashPassword =passwordEncoder.encode(password);
        newBlogUser.setPassword(hashPassword);

        try {
            blogUserRepository.save(newBlogUser);
            return new RegisterSuccess("Account with username: {" + username + "} Registered successfully");

        } catch (Exception e) {
            throw new RegistrationFailureException("Registration failed: " + e.getMessage());
        }
    }

}

