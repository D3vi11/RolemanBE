package com.example.charr;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CharController {

    @GetMapping
    public String getCharacter(){
        return "";
    }
    @PostMapping
    public void createCharacter(){

    }
    @PutMapping
    public void updateCharacter(){

    }
}
