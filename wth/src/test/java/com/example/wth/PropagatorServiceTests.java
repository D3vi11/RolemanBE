package com.example.wth;

import com.example.wth.dto.PreferenceDto;
import com.example.wth.dto.WeatherSetDto;
import com.example.wth.exception.UnableToDeleteException;
import com.example.wth.exception.UnableToSaveException;
import com.example.wth.service.PreferenceService;
import com.example.wth.service.PropagatorService;
import com.example.wth.service.WeatherService;
import com.mongodb.MongoException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropagatorServiceTests {
    @Mock
    PreferenceService preferenceService;
    @Mock
    WeatherService weatherService;

    @InjectMocks
    PropagatorService propagatorService;

    String campaignId = "campaignId";
    WeatherSetDto weatherSetDto = new WeatherSetDto(campaignId, new ArrayList<>());
    PreferenceDto preferenceDto = new PreferenceDto(campaignId, false);

    @Nested
    public class createTests {

        @Test
        public void createTest() {
            propagatorService.create(campaignId);
            verify(weatherService).createWeather(weatherSetDto);
            verify(preferenceService).createPreference(preferenceDto);
        }

        @Test
        public void databaseErrorWeatherTest() {
            doThrow(new MongoException("")).when(weatherService).createWeather(weatherSetDto);
            assertThrows(UnableToSaveException.class, () -> propagatorService.create(campaignId));
            verify(weatherService).createWeather(weatherSetDto);
            verify(preferenceService, never()).createPreference(preferenceDto);
        }

        @Test
        public void databaseErrorPreferenceTest() {
            doThrow(new MongoException("")).when(preferenceService).createPreference(preferenceDto);
            assertThrows(UnableToSaveException.class, () -> propagatorService.create(campaignId));
            verify(weatherService).createWeather(weatherSetDto);
            verify(preferenceService).createPreference(preferenceDto);
        }
    }

    @Nested
    public class DeleteTests {

        @Test
        public void deleteTest() {
            propagatorService.delete(campaignId);
            verify(weatherService).deleteWeather(campaignId);
            verify(preferenceService).deletePreference(campaignId);
        }

        @Test
        public void databaseErrorWeatherTest() {
            doThrow(new MongoException("")).when(weatherService).deleteWeather(campaignId);
            assertThrows(UnableToDeleteException.class, () -> propagatorService.delete(campaignId));
            verify(weatherService).deleteWeather(campaignId);
            verify(preferenceService, never()).deletePreference(campaignId);
        }

        @Test
        public void databaseErrorPreferenceTest() {
            doThrow(new MongoException("")).when(preferenceService).deletePreference(campaignId);
            assertThrows(UnableToDeleteException.class, () -> propagatorService.delete(campaignId));
            verify(weatherService).deleteWeather(campaignId);
            verify(preferenceService).deletePreference(campaignId);
        }
    }
}
