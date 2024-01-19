package com.example.wth.service;

import com.example.wth.dto.PreferenceDto;
import com.example.wth.dto.WeatherSetDto;
import com.example.wth.entity.Weather;
import com.example.wth.exception.UnableToDeleteException;
import com.example.wth.exception.UnableToSaveException;
import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PropagatorService {
    private final WeatherService weatherService;
    private final PreferenceService preferenceService;
    public void create(String campaignId){
        try {
            weatherService.createWeather(new WeatherSetDto(campaignId,new ArrayList<>()));
            preferenceService.createPreference(new PreferenceDto(campaignId,false));
        }catch (MongoException e){
            throw new UnableToSaveException("Błąd zapisu do bazy");
        }
    }

    public void delete(String campaignId){
        try{
            weatherService.deleteWeather(campaignId);
            preferenceService.deletePreference(campaignId);
        }catch (MongoException e){
            throw new UnableToDeleteException("Błąd podczas usuwania z bazy");
        }
    }
}
