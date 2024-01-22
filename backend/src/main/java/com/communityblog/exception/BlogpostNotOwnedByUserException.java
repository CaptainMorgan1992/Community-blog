package com.communityblog.exception;

public class BlogpostNotOwnedByUserException extends RuntimeException{
    public BlogpostNotOwnedByUserException(Long id) {
        super("Blogpost with id '" + id + "' does not belong to the user!");

    }
}
