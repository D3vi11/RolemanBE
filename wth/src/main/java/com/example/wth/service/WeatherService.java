package com.example.wth.service;

import com.example.wth.entity.Preference;
import com.example.wth.entity.Weather;
import com.example.wth.exception.CampaignNotFoundException;
import com.example.wth.exception.WeatherNotFoundException;
import com.example.wth.repository.PreferenceRepository;
import com.example.wth.repository.WeatherRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
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

    public String getWeather(Integer campaignId){
        Preference preference = preferenceRepository.findByCampaignId(campaignId)
                .orElseThrow(() -> new CampaignNotFoundException("Nie znaleziono kampanii"));

        if(preference.getIsExternal()){
            return getExternalWeather();
        }else {
            Random random = new Random();
            Weather weather = weatherRepository.findByCampaignId(campaignId)
                    .orElseThrow(() -> new CampaignNotFoundException("Nie znaleziono Kampanii"));
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
}
