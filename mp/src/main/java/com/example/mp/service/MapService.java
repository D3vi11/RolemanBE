package com.example.mp.service;

import com.example.mp.dto.MapDto;
import com.example.mp.entity.Map;
import com.example.mp.exception.IncorrectIdException;
import com.example.mp.exception.NotFoundException;
import com.example.mp.exception.UnableToDeleteException;
import com.example.mp.exception.UnableToSaveException;
import com.example.mp.repository.MapRepository;
import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapService {
    private final MapRepository mapRepository;

    public MapDto readMap(String campaignId) {
        Map map = mapRepository.findByCampaignId(campaignId)
                .orElseThrow(() -> new NotFoundException("Nie znaleziono danych kampanii"));
        return mapToDto(map);
    }

    public void createMap(MapDto mapDto) {
        try {
            mapRepository.save(mapToMap(mapDto));
        }catch (MongoException e){
            throw new UnableToSaveException("Błąd podczas zapisu");
        }
    }

    public void modifyMap(String campaignId, MapDto mapDto){
        if(!campaignId.equals(mapDto.getCampaignId())){
            throw new IncorrectIdException("Id kampanii nie są takie same");
        }
        Map map = mapRepository.findByCampaignId(campaignId)
                .orElseThrow(() -> new NotFoundException("Nie znaleziono danych kampanii"));
        map.setImageUrl(mapDto.getImageUrl());
        map.setMapSize(mapDto.getMapSize());
        map.setCurrentLocation(mapDto.getCurrentLocation());
        try{
            mapRepository.save(map);
        }catch (MongoException e){
            throw new UnableToSaveException("Błąd podczas zapisu");
        }
    }

    public void deleteMap(String campaignId){
        mapRepository.findByCampaignId(campaignId)
                .orElseThrow(() -> new NotFoundException("Zasób nie istnieje lub został już usunięty"));
        try{
            mapRepository.deleteByCampaignId(campaignId);
        }catch (MongoException e){
            throw new UnableToDeleteException("Błąd podczas usuwania");
        }
    }

    private MapDto mapToDto(Map map) {
        return new MapDto(map.getCampaignId(), map.getImageUrl(), map.getMapSize(), map.getCurrentLocation());
    }

    private Map mapToMap(MapDto mapDto) {
        return new Map(mapDto.getCampaignId(), mapDto.getImageUrl(), mapDto.getMapSize(), mapDto.getCurrentLocation());
    }
}
