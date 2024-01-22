package com.communityblog.controller;


import com.communityblog.model.Blogpost;
import com.communityblog.service.BlogpostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/blogpost")
public class BlogpostController {

    public final BlogpostService blogpostService;

    @Autowired
    public BlogpostController(BlogpostService blogpostService) {
        this.blogpostService = blogpostService;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createBlogpost(@Valid @RequestBody Blogpost blogpost, Principal principal) {
        blogpostService.createBlogPost(blogpost, principal);
        return new ResponseEntity<>("Blogpost created", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Blogpost>> getAllBlogPosts() {
        return new ResponseEntity<>(blogpostService.getAllBlogPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blogpost> getBlogPost(@PathVariable Long id) {
        Blogpost blogpost = blogpostService.getBlogPostById(id);
        return new ResponseEntity<>(blogpost, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteBlogpost(@PathVariable Long id, Principal principal) {
        blogpostService.deleteBlogpost(id, principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
