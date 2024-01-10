package com.example.wth.controller;

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
    private final RestTemplate restTemplate;

    @GetMapping("weather")
    public ResponseEntity<String> getWeather(@RequestParam Integer campaignId){
        weatherService.getWeather(campaignId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jsonObject.toString());
    }
}
