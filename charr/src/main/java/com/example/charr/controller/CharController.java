package com.example.charr.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class CharController {

    @GetMapping
    public String getCharacter(){
        return "character";
    }
    @PostMapping
    public void createCharacter(){

    }
    @PutMapping
    public void updateCharacter(){

    }
}
