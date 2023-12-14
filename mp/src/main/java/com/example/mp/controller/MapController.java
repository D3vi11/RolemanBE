package com.example.mp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class MapController {
    @GetMapping
    public ResponseEntity<String> getMap(){
        return new ResponseEntity<>("Work in progress", HttpStatus.I_AM_A_TEAPOT);
    }
    @PostMapping
    public ResponseEntity<String> addMap(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
    @PatchMapping
    public ResponseEntity<String> updateMap(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
    @DeleteMapping
    public ResponseEntity<String> removeMap(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
}
