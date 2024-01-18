package com.communityblog.controller;


import com.communityblog.JWT.JwtTokenProvider;
import com.communityblog.model.Blogpost;
import com.communityblog.service.BlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogpost")
public class BlogpostController {

    public final BlogpostService blogpostService;

    public final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public BlogpostController(BlogpostService blogpostService, JwtTokenProvider jwtTokenProvider) {
        this.blogpostService = blogpostService;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createBlogpost(@RequestBody Blogpost blogpost, @RequestHeader("Authorization") String token) {

        boolean isValid = jwtTokenProvider.validateToken(token);
        boolean isTokenInvalidated = jwtTokenProvider.isTokenInvalidated(token);
        System.out.println(isTokenInvalidated);
        if (isValid && !isTokenInvalidated) {
            blogpostService.createBlogPost(blogpost, token);
            return new ResponseEntity<>("Blogpost created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Blogpost>> getAllBlogPosts() {
        return new ResponseEntity<>(blogpostService.getAllBlogPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blogpost> getBlogPost(@PathVariable Integer id) {
        Blogpost blogpost = blogpostService.getBlogPostById(id);
        return new ResponseEntity<>(blogpost, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteBlogpost(@PathVariable Integer id) {
        blogpostService.deleteBlogpost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
