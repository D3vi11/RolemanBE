package com.example.wth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
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
