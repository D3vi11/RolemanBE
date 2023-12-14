package com.example.gen.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class GenController {

    @GetMapping
    public ResponseEntity<String> getGen(){
        return new ResponseEntity<>("Work in progress", HttpStatus.I_AM_A_TEAPOT);
    }
}
