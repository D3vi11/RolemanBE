package com.example.wth.service;

import com.example.wth.dto.WeatherDto;
import com.example.wth.dto.WeatherSetDto;
import com.example.wth.entity.Preference;
import com.example.wth.entity.Weather;
import com.example.wth.exception.*;
import com.example.wth.repository.PreferenceRepository;
import com.example.wth.repository.WeatherRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${weather.api.key}")
    private String key;
    private final WeatherRepository weatherRepository;
    private final PreferenceRepository preferenceRepository;
    private final RestTemplate restTemplate;

    public String getWeather(String campaignId){
        Preference preference = preferenceRepository.findByCampaignId(campaignId)
                .orElseThrow(() -> new PreferenceNotFoundException("Nie znaleziono preferencji dla tej kampanii"));

        if(preference.getIsExternal()){
            return getExternalWeather();
        }else {
            Random random = new Random();
            Weather weather = weatherRepository.findByCampaignId(campaignId)
                    .orElseThrow(() -> new WeatherNotFoundException("Nie znaleziono danych pogodowych dla tej kampanii"));
            List<String> list = weather.getWeatherList()
                    .stream()
                    .sorted((a, b) -> random.nextInt(3) - 1)
                    .limit(1)
                    .toList();
            if(list.isEmpty()){
                throw new WeatherNotFoundException("Brak pogody w bazie danych");
            }
            return list.get(0);
        }
    }

    public void createWeather(WeatherSetDto weatherSetDto){
        if(weatherRepository.findByCampaignId(weatherSetDto.getCampaignId()).isPresent()){
            throw new WeatherExistsException("Pogoda dla kampanii o podanym id już istnieje");
        }
        weatherRepository.save(mapToWeather(weatherSetDto));
    }

    public void modifyWeather(String campaignId,WeatherSetDto weatherSetDto){
        if(!campaignId.equals(weatherSetDto.getCampaignId())){
            throw new IdDoesntMatchException("Id kampanii są różne");
        }
        Weather weather = weatherRepository.findByCampaignId(campaignId)
                .orElseThrow(() -> new WeatherExistsException("Dane pogodowe dla tej kampanii nie istnieją"));
        weather.setWeatherList(weatherSetDto.getWeatherList());
        try{
            weatherRepository.save(weather);
        }catch (MongoException e){
            throw new UnableToSaveException("Błąd podczas zapisu");
        }
    }

    public void deleteWeather(String campaignId){
        try{
            weatherRepository.deleteByCampaignId(campaignId);
        }catch (MongoException e){
            throw new UnableToDeleteException("Błąd podczas usuwania danych pogodowych");
        }
    }

    private String getExternalWeather(){
        ResponseEntity<String> entity = restTemplate.getForEntity("http://api.weatherstack.com/current?access_key="+key+"&query=Wroclaw", String.class);

        String body = entity.getBody();
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        return jsonObject
                .get("current")
                .getAsJsonObject()
                .get("weather_descriptions")
                .getAsString();
    }

    private Weather mapToWeather(WeatherSetDto weatherSetDto){
        return new Weather(weatherSetDto.getCampaignId(),weatherSetDto.getWeatherList());
    }
}
