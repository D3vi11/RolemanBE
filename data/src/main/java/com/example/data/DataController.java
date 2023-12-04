package com.example.data;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class DataController {

    @GetMapping
    public String getData(){
        return "Data";
    }
    @PostMapping
    public void postData(){

    }
    @PutMapping
    public void putData(){

    }
    @DeleteMapping
    public void deleteData(){

    }
}
