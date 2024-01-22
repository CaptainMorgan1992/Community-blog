package com.communityblog.service;

import com.communityblog.exception.BlogpostNotFoundException;
import com.communityblog.exception.BlogpostNotOwnedByUserException;
import com.communityblog.model.Blogpost;
import com.communityblog.model.User;
import com.communityblog.repository.BlogpostRepository;
import com.communityblog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
public class BlogpostService {

    public final BlogpostRepository blogPostRepository;
    public final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public BlogpostService(BlogpostRepository blogPostRepository, UserService userService, UserRepository userRepository) {
        this.blogPostRepository = blogPostRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    public void createBlogPost(Blogpost blogPost, Principal principal) {
        String username = principal.getName();
        System.out.println(principal.getName());
        User user = userRepository.findByUserName(username);
        System.out.println(user);
        blogPost.setAuthor(user);
        blogPostRepository.save(blogPost);
    }

    public List<Blogpost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    public Blogpost getBlogPostById(Long id) {
        return blogPostRepository.findById(id).orElseThrow(() -> new BlogpostNotFoundException(id));
    }


    public void deleteBlogpost(Long id, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUserName(username);
        Blogpost blogpost = blogPostRepository.findById(id).orElseThrow(() -> new BlogpostNotFoundException(id));

        if(!blogpost.getAuthor().equals(user)){
            throw new BlogpostNotOwnedByUserException(id);
        }
        blogPostRepository.delete(blogpost);
    }

}
