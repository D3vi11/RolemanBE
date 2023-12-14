package com.example.charr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class CharController {

    @GetMapping
    public ResponseEntity<String> getCharacter(){
        return new ResponseEntity<>("Work in progress", HttpStatus.I_AM_A_TEAPOT);
    }
    @PostMapping
    public ResponseEntity<String> createCharacter(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
    @PutMapping
    public ResponseEntity<String> updateCharacter(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
}
