package com.example.cal.controller;

import com.example.cal.ResponseObject;
import com.example.cal.dto.CalendarDto;
import com.example.cal.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;

    @PutMapping("nextDay")
    public ResponseEntity<ResponseObject> nextDay(@RequestParam String campaignId){
        calendarService.nextDay(campaignId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }

    @GetMapping
    public ResponseEntity<CalendarDto> getCalendar(@RequestParam String campaignId){
        CalendarDto calendarDto = calendarService.readCalendar(campaignId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(calendarDto);
    }
    @PostMapping
    public ResponseEntity<ResponseObject> addCalendar(@RequestBody CalendarDto calendarDto){
        calendarService.createCalendar(calendarDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }
    @PutMapping
    public ResponseEntity<ResponseObject> updateCalendar(@RequestParam String campaignId, @RequestBody CalendarDto calendarDto){
        calendarService.modifyCalendar(campaignId, calendarDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }
    @DeleteMapping
    public ResponseEntity<ResponseObject> removeCalendar(@RequestParam String campaignId){
        calendarService.deleteCalendar(campaignId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }
}
