package com.example.cal;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendar")
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
