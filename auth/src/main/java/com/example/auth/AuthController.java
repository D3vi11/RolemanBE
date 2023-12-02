package com.example.auth;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AuthController {

    @PostMapping("login")
    public void login(){

    }
    @PostMapping("register")
    public void register(){

    }
    @PutMapping
    public void update(){

    }
    @DeleteMapping
    public void delete(){

    }
}
