package com.communityblog.exception;

public class BlogpostNotFoundException extends RuntimeException {
    public BlogpostNotFoundException(Long id) {
        super("Blogpost with id '" + id + "' does not exist!");
    }
}
