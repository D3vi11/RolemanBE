package com.example.wth.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class WeatherController {

    @GetMapping
    public String getWeather(){
        return "Weather";
    }
    @PostMapping
    public void addWeather(){

    }
    @PatchMapping
    public void updateWeather(){

    }
    @DeleteMapping
    public void removeWeather(){

    }
}
