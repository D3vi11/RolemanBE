package com.example.cal.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class CalendarController {

    @Value("${mongo.password}")
    private String value;
    @GetMapping
    public String getCalendar(){
        return "calendar "+value;
    }
    @PostMapping
    public void addCalendar(){

    }
    @PatchMapping
    public void updateCalendar(){

    }
    @DeleteMapping
    public void removeCalendar(){

    }
}
