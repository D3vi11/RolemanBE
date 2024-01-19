package com.example.wth.controller;

import com.example.wth.ResponseObject;
import com.example.wth.dto.WeatherDto;
import com.example.wth.dto.WeatherSetDto;
import com.example.wth.service.WeatherService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<ResponseObject> getWeather(@RequestParam String campaignId){
        String weather = weatherService.getWeather(campaignId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(weather,HttpStatus.OK));
    }

    @PostMapping
    public ResponseEntity<ResponseObject> postWeather(@RequestBody WeatherSetDto weatherSetDto){
        weatherService.createWeather(weatherSetDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }
    @PutMapping
    public ResponseEntity<ResponseObject> putWeather(@RequestParam String campaignId,@RequestBody WeatherSetDto weatherSetDto){
        weatherService.modifyWeather(campaignId, weatherSetDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }

    @DeleteMapping
    public ResponseEntity<ResponseObject> deleteWeather(String campaignId){
        weatherService.deleteWeather(campaignId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }
}
