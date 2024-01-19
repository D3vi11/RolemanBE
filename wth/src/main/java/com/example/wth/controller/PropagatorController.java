package com.example.wth.controller;

import com.example.wth.ResponseObject;
import com.example.wth.dto.PropagatorDto;
import com.example.wth.dto.WeatherSetDto;
import com.example.wth.service.PreferenceService;
import com.example.wth.service.PropagatorService;
import com.example.wth.service.WeatherService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("propagate")
@RequiredArgsConstructor
public class PropagatorController {
    private final PropagatorService propagatorService;
    @PostMapping
    public ResponseEntity<ResponseObject> createData(@RequestBody PropagatorDto propagatorDto){
        propagatorService.create(propagatorDto.campaignId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }

    @DeleteMapping
    public ResponseEntity<ResponseObject> deleteData(@RequestParam String campaignId){
        propagatorService.delete(campaignId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.OK));
    }
}
