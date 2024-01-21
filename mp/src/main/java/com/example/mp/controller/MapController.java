package com.example.mp.controller;

import com.example.mp.ResponseObject;
import com.example.mp.dto.MapDto;
import com.example.mp.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;
    @GetMapping
    public ResponseEntity<MapDto> getMap(@RequestParam String campaignId){
        MapDto mapDto = mapService.readMap(campaignId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapDto);
    }
    @PostMapping
    public ResponseEntity<ResponseObject> postMap(@RequestBody MapDto mapDto){
        mapService.createMap(mapDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }
    @PutMapping
    public ResponseEntity<ResponseObject> putMap(@RequestParam String campaignId, @RequestBody MapDto mapDto){
        mapService.modifyMap(campaignId,mapDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }
    @DeleteMapping
    public ResponseEntity<ResponseObject> deleteMap(@RequestParam String campaignId){
        mapService.deleteMap(campaignId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }
}
