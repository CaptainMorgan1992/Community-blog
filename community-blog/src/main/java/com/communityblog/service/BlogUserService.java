package com.communityblog.service;

import com.communityblog.dto.RegisterSuccess;
import com.communityblog.exception.RegistrationFailureException;
import com.communityblog.model.BlogUser;
import com.communityblog.repository.BlogUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogUserService {
    private final BlogUserRepository blogUserRepository;

    @Autowired
    public BlogUserService(BlogUserRepository blogUserRepository) {
        this.blogUserRepository = blogUserRepository;
    }

    public RegisterSuccess register(String username, String password, String email ) {
        BlogUser newBlogUser = new BlogUser(username, password, email, "USER");
        try {
            blogUserRepository.save(newBlogUser);
            return new RegisterSuccess("Account with username: {" + username + "} Registered successfully");

        } catch (Exception e) {
            throw new RegistrationFailureException("Registration failed: " + e.getMessage());
        }
    }



}

