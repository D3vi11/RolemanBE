package com.example.mp;

import com.example.mp.dto.MapDto;
import com.example.mp.entity.Coordinate;
import com.example.mp.service.MapService;
import com.example.mp.service.PropagatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PropagatorServiceTests {
    @Mock
    MapService mapService;

    @InjectMocks
    PropagatorService propagatorService;

    private static final String campaignId = "campaignId";

    MapDto mapDto = new MapDto(campaignId, "", new Coordinate(0,0), new Coordinate(0,0));

    @Test
    public void createTest(){
        propagatorService.create(campaignId);
        verify(mapService).createMap(mapDto);
    }

    @Test
    public void deleteTest(){
        propagatorService.delete(campaignId);
        verify(mapService).deleteMap(campaignId);
    }
}
