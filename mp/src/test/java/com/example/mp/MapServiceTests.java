package com.example.mp;

import com.example.mp.dto.MapDto;
import com.example.mp.entity.Coordinate;
import com.example.mp.entity.Map;
import com.example.mp.exception.IncorrectIdException;
import com.example.mp.exception.NotFoundException;
import com.example.mp.exception.UnableToDeleteException;
import com.example.mp.exception.UnableToSaveException;
import com.example.mp.repository.MapRepository;
import com.example.mp.service.MapService;
import com.mongodb.MongoException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MapServiceTests {
    @Mock
    MapRepository mapRepository;

    @InjectMocks
    MapService mapService;

    String campaignId = "campaignId";
    String imageUrl = "imageUrl";
    Coordinate mapSize = new Coordinate(10, 10);
    Coordinate currentLocation = new Coordinate(1, 1);

    @Nested
    public class ReadMapTests {
        Map map = new Map(campaignId, imageUrl, mapSize, currentLocation);
        MapDto mapDto = new MapDto(campaignId, imageUrl, mapSize, currentLocation);

        @Test
        public void readMapTest() {
            when(mapRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(map));
            MapDto dto = mapService.readMap(campaignId);
            assertEquals(mapDto, dto);
        }

        @Test
        public void mapNotFoundTest() {
            when(mapRepository.findByCampaignId(campaignId)).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> mapService.readMap(campaignId));
        }
    }

    @Nested
    public class CreateMapTests {
        Map map = new Map(campaignId, imageUrl, mapSize, currentLocation);
        MapDto mapDto = new MapDto(campaignId, imageUrl, mapSize, currentLocation);

        @Test
        public void createMapTest() {
            mapService.createMap(mapDto);
            verify(mapRepository).save(map);
        }

        @Test
        public void databaseErrorTest() {
            when(mapRepository.save(map)).thenThrow(new MongoException(""));
            assertThrows(UnableToSaveException.class, () -> mapService.createMap(mapDto));
            verify(mapRepository).save(map);
        }
    }

    @Nested
    public class ModifyMapTests {
        Map map = new Map(campaignId, imageUrl, mapSize, currentLocation);
        MapDto mapDto = new MapDto(campaignId, imageUrl, mapSize, currentLocation);

        @Test
        public void modifyMapTest() {
            when(mapRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(map));
            mapService.modifyMap(campaignId, mapDto);
            ArgumentCaptor<Map> mapCaptor = ArgumentCaptor.forClass(Map.class);
            verify(mapRepository).save(mapCaptor.capture());
            assertAll("Map",
                    () -> assertEquals(map.getImageUrl(), mapCaptor.getValue().getImageUrl()),
                    () -> assertEquals(map.getMapSize(), mapCaptor.getValue().getMapSize()),
                    () -> assertEquals(map.getCurrentLocation(), mapCaptor.getValue().getCurrentLocation())
            );
        }

        @Test
        public void incorrectCampaignIdTest() {
            assertThrows(IncorrectIdException.class, () -> mapService.modifyMap(campaignId + "1", mapDto));
            ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<Map> mapCaptor = ArgumentCaptor.forClass(Map.class);
            verify(mapRepository, never()).findByCampaignId(stringCaptor.capture());
            verify(mapRepository, never()).save(mapCaptor.capture());
        }

        @Test
        public void mapNotFoundTest() {
            when(mapRepository.findByCampaignId(campaignId)).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> mapService.modifyMap(campaignId, mapDto));
            ArgumentCaptor<Map> mapCaptor = ArgumentCaptor.forClass(Map.class);
            verify(mapRepository, never()).save(mapCaptor.capture());
        }

        @Test
        public void databaseErrorTest() {
            when(mapRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(map));
            when(mapRepository.save(map)).thenThrow(new MongoException(""));
            assertThrows(UnableToSaveException.class, () -> mapService.modifyMap(campaignId, mapDto));
        }

    }

    @Nested
    public class DeleteMapTests {
        Map map = new Map(campaignId, imageUrl, mapSize, currentLocation);

        @Test
        public void deleteMapTest() {
            when(mapRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(map));
            mapService.deleteMap(campaignId);
            verify(mapRepository).deleteByCampaignId(campaignId);
        }

        @Test
        public void mapNotFoundTest() {
            when(mapRepository.findByCampaignId(campaignId)).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> mapService.deleteMap(campaignId));
            verify(mapRepository, never()).deleteByCampaignId(campaignId);
        }

        @Test
        public void databaseErrorTest() {
            when(mapRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(map));
            doThrow(new MongoException("")).when(mapRepository).deleteByCampaignId(campaignId);
            assertThrows(UnableToDeleteException.class, () -> mapService.deleteMap(campaignId));
        }
    }

}
