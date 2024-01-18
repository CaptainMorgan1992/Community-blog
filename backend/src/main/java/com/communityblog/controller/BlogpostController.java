package com.communityblog.controller;


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

    @Autowired
    public BlogpostController(BlogpostService blogpostService) {
        this.blogpostService = blogpostService;
    }


    @PostMapping("/create/{authorId}")
    public ResponseEntity<String> createBlogpost(@RequestBody Blogpost blogpost, @PathVariable Integer authorId) {
        blogpostService.createBlogPost(blogpost, authorId);
        return new ResponseEntity<>("Blogpost created", HttpStatus.CREATED);
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
