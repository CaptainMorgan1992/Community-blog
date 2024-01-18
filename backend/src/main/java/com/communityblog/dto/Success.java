package com.communityblog.dto;

import lombok.Getter;

@Getter
abstract public class Success{

    private String message;

    public Success(String message) {
        this.message = message;
    }
}