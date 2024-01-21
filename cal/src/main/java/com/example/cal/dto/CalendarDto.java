package com.example.cal.dto;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;
@Data
public class CalendarDto {
    @NonNull
    private String campaignId;
    @NonNull
    private LocalDate currentDay;
    @NonNull
    private List<String> events;
}
