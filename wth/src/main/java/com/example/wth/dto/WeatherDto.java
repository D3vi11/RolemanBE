package com.example.wth.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class WeatherDto {
    @NonNull
    private String currentWeather;
}
