package com.example.wth;

import com.example.wth.dto.PreferenceDto;
import com.example.wth.dto.WeatherSetDto;
import com.example.wth.entity.Weather;
import com.example.wth.exception.*;
import com.example.wth.repository.WeatherRepository;
import com.example.wth.service.PreferenceService;
import com.example.wth.service.WeatherService;
import com.mongodb.MongoException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTests {
    @Mock
    WeatherRepository weatherRepository;
    @Mock
    PreferenceService preferenceService;
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    WeatherService weatherService;

    String campaignId = "campaignId";
    String weatherDescription = "Sunny";

    @Nested
    public class GetWeatherTests{
        List<String> weatherList = List.of("weather");
        Weather weather = new Weather(campaignId, weatherList);

        @Test
        public void getInternalWeatherTest(){
            when(preferenceService.readPreference(campaignId)).thenReturn(new PreferenceDto(campaignId, false));
            when(weatherRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(weather));
            String weatherResult = weatherService.getWeather(campaignId);
            assertEquals(weatherList.get(0), weatherResult);
        }

        @Test
        public void getExternalWeatherTest(){
            when(preferenceService.readPreference(campaignId)).thenReturn(new PreferenceDto(campaignId, true));
            ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
            when(restTemplate.getForEntity(argumentCaptor.capture(), eq(String.class))).thenReturn(ResponseEntity.of(Optional.of(getExternalJson(weatherDescription))));
            String weatherResult = weatherService.getWeather(campaignId);
            assertEquals(weatherDescription, weatherResult);
        }

        @Test
        public void weatherNotFoundTest(){
            when(preferenceService.readPreference(campaignId)).thenReturn(new PreferenceDto(campaignId, false));
            when(weatherRepository.findByCampaignId(campaignId)).thenReturn(Optional.empty());
            assertThrows(WeatherNotFoundException.class, () -> weatherService.getWeather(campaignId));
        }

        @Test
        public void weatherListNotFoundTest(){
            when(preferenceService.readPreference(campaignId)).thenReturn(new PreferenceDto(campaignId, false));
            when(weatherRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(new Weather(campaignId, new ArrayList<>())));
            assertThrows(WeatherNotFoundException.class, () -> weatherService.getWeather(campaignId));
        }

        @Test
        public void externalWeatherNotFoundNullTest(){
            when(preferenceService.readPreference(campaignId)).thenReturn(new PreferenceDto(campaignId, true));
            ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
            when(restTemplate.getForEntity(argumentCaptor.capture(), eq(String.class))).thenReturn(ResponseEntity.of(Optional.empty()));
            assertThrows(WeatherNotFoundException.class, () -> weatherService.getWeather(campaignId));
        }

        @Test
        public void externalWeatherIncorrectResponseTest(){
            when(preferenceService.readPreference(campaignId)).thenReturn(new PreferenceDto(campaignId, true));
            ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
            when(restTemplate.getForEntity(argumentCaptor.capture(), eq(String.class))).thenReturn(ResponseEntity.of(Optional.of("{\"a\": 1}")));
            assertThrows(WeatherNotFoundException.class, () -> weatherService.getWeather(campaignId));
        }
    }

    @Nested
    public class CreateWeatherTests{
        List<String> weatherList = List.of("weather");
        WeatherSetDto weatherSetDto = new WeatherSetDto(campaignId, weatherList);
        Weather weather = new Weather(campaignId, weatherList);

        @Test
        public void createWeatherTest(){
            when(weatherRepository.findByCampaignId(campaignId)).thenReturn(Optional.empty());
            weatherService.createWeather(weatherSetDto);
        }

        @Test
        public void weatherAlreadyExistsTest(){
            when(weatherRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(weather));
            assertThrows(WeatherExistsException.class, () -> weatherService.createWeather(weatherSetDto));
        }
    }

    @Nested
    public class ModifyWeatherTests{
        List<String> weatherList = List.of("weather");
        WeatherSetDto weatherSetDto = new WeatherSetDto(campaignId, weatherList);
        Weather weather = new Weather(campaignId, weatherList);
        @Test
        public void modifyWeatherTest(){
            when(weatherRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(weather));
            weatherService.modifyWeather(campaignId, weatherSetDto);
            verify(weatherRepository).save(weather);
        }

        @Test
        public void campaignIdNotMatchingTest(){
            assertThrows(IdDoesntMatchException.class,() -> weatherService.modifyWeather(campaignId + "1", weatherSetDto));
            verify(weatherRepository, never()).findByCampaignId(campaignId);
            verify(weatherRepository, never()).save(weather);
        }

        @Test
        public void weatherNotFoundTest(){
            when(weatherRepository.findByCampaignId(campaignId)).thenReturn(Optional.empty());
            assertThrows(WeatherExistsException.class,() -> weatherService.modifyWeather(campaignId, weatherSetDto));
            verify(weatherRepository).findByCampaignId(campaignId);
            verify(weatherRepository, never()).save(weather);
        }

        @Test
        public void databaseErrorTest(){
            when(weatherRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(weather));
            when(weatherRepository.save(weather)).thenThrow(new MongoException(""));
            assertThrows(UnableToSaveException.class,() -> weatherService.modifyWeather(campaignId, weatherSetDto));
            verify(weatherRepository).findByCampaignId(campaignId);
            verify(weatherRepository).save(weather);
        }
    }

    @Nested
    public class DeleteWeatherTests{
        @Test
        public void deleteWeatherTest(){
            weatherService.deleteWeather(campaignId);
            verify(weatherRepository).deleteByCampaignId(campaignId);
        }

        @Test
        public void databaseErrorTest(){
            doThrow(new MongoException("")).when(weatherRepository).deleteByCampaignId(campaignId);
            assertThrows(UnableToDeleteException.class, () -> weatherService.deleteWeather(campaignId));
        }
    }

    private String getExternalJson(String weatherDescription){
        return """
                {
                  "request": {
                    "type": "City",
                    "query": "New York, United States of America",
                    "language": "en",
                    "unit": "m"
                  },
                  "location": {
                    "name": "New York",
                    "country": "United States of America",
                    "region": "New York",
                    "lat": "40.714",
                    "lon": "-74.006",
                    "timezone_id": "America/New_York",
                    "localtime": "2019-09-08 09:36",
                    "localtime_epoch": 1567935360,
                    "utc_offset": "-4.0"
                  },
                  "current": {
                    "observation_time": "01:36 PM",
                    "temperature": 18,
                    "weather_code": 113,
                    "weather_icons": [
                      "https://assets.weatherstack.com/images/wsymbols01_png_64/wsymbol_0001_sunny.png"
                    ],
                    "weather_descriptions": [
                      """+ weatherDescription +"""
                    ],
                    "wind_speed": 7,
                    "wind_degree": 270,
                    "wind_dir": "W",
                    "pressure": 1012,
                    "precip": 0,
                    "humidity": 60,
                    "cloudcover": 0,
                    "feelslike": 17,
                    "uv_index": 5
                  }
                }
                """;
    }
}
