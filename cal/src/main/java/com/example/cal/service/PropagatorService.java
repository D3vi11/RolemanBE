package com.example.cal.service;

import com.example.cal.dto.CalendarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PropagatorService {
    private final CalendarService calendarService;

    public void create(String campaignId){
        calendarService.createCalendar(new CalendarDto(campaignId, LocalDate.of(2000,1,1),new ArrayList<>()));
    }
    public void delete(String campaignId){
        calendarService.deleteCalendar(campaignId);
    }
}
