package com.example.gen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GenController {

    @GetMapping
    public String getGen(){
        return "Generator";
    }
}
