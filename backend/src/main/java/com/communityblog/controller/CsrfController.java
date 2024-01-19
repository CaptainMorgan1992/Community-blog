package com.communityblog.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;

public class CsrfController {

    @GetMapping("/csrf")
    public CsrfToken csrfToken(CsrfToken csrfToken){
        return csrfToken;
    }
}
