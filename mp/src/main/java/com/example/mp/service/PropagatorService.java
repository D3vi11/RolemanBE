package com.example.mp.service;

import com.example.mp.dto.MapDto;
import com.example.mp.entity.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropagatorService {
    private final MapService mapService;

    public void create(String campaignId){
        mapService.createMap(new MapDto(campaignId,"",new Coordinate(0,0),new Coordinate(0,0)));
    }

    public void delete(String campaignId){
        mapService.deleteMap(campaignId);
    }
}
