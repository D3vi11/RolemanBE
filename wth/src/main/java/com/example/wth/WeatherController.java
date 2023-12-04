package com.example.wth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WeatherController {

    @Value("${test.value}")
    private String value;

    @GetMapping("test")
    public String test(){
        return value;
    }
    @GetMapping
    public String getWeather(){
        return "";
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
