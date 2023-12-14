package com.example.data.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class DataController {

    @GetMapping
    public ResponseEntity<String> getData(){
        return new ResponseEntity<>("Work in progress", HttpStatus.I_AM_A_TEAPOT);
    }
    @PostMapping
    public ResponseEntity<String> postData(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
    @PutMapping
    public ResponseEntity<String> putData(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteData(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
}
