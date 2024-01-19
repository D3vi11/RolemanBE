package com.example.wth.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class WeatherSetDto {
    @NonNull
    private String campaignId;
    @NonNull
    private List<String> weatherList;
}
