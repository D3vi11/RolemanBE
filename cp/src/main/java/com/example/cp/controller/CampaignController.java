package com.example.cp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class CampaignController {
    @GetMapping
    public ResponseEntity<String> getCampaign(){
        return new ResponseEntity<>("Work in progress", HttpStatus.I_AM_A_TEAPOT);
    }
    @PostMapping
    public ResponseEntity<String> addCampaign(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
    @PatchMapping
    public ResponseEntity<String> updateCampaign(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
    @DeleteMapping
    public ResponseEntity<String> removeCampaign(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
}
