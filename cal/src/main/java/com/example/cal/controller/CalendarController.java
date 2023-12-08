package com.example.cal.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class CalendarController {
    @GetMapping
    public String getCalendar(){
        return "calendar";
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
