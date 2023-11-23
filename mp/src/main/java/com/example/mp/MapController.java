package com.example.mp;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MapController {
    @GetMapping
    public String getMap(){
        return "";
    }
    @PostMapping
    public void addMap(){

    }
    @PatchMapping
    public void updateMap(){

    }
    @DeleteMapping
    public void removeMap(){

    }
}
