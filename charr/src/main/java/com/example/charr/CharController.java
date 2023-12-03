package com.example.charr;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/character")
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
