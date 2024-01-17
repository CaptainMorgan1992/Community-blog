package com.communityblog.service;

import com.communityblog.model.Blogpost;
import com.communityblog.model.User;
import com.communityblog.repository.BlogpostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogpostService {

    public final BlogpostRepository blogPostRepository;
    public final UserService userService;

    @Autowired
    public BlogpostService(BlogpostRepository blogPostRepository, UserService userService) {
        this.blogPostRepository = blogPostRepository;
        this.userService = userService;
    }


    public void createBlogPost(Blogpost blogPost, Integer userId) {
        //blogPost.setDate(LocalDateTime.now());

        User user = userService.getBlogUserById(userId);
        blogPost.setAuthor(user);
        blogPostRepository.save(blogPost);
    }

    public List<Blogpost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    public Blogpost getBlogPostById(Integer id) {
        return blogPostRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Blogpost not found"));
    }

    public void deleteBlogpost(Integer id) {
        blogPostRepository.deleteById(id);
    }
}
