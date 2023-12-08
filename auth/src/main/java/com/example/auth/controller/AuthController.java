package com.example.auth.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class AuthController {

    @GetMapping
    public String test(){
        return "Authorization";
    }

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
