package com.example.wth.controller;

import com.example.wth.ResponseObject;
import com.example.wth.dto.PreferenceDto;
import com.example.wth.service.PreferenceService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("preference")
public class PreferenceController {
    PreferenceService preferenceService;

    @GetMapping
    public ResponseEntity<PreferenceDto> getPreference(String campaignId){
        PreferenceDto preferenceDto = preferenceService.readPreference(campaignId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(preferenceDto);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createPreference(@RequestBody PreferenceDto preferenceDto){
        preferenceService.createPreference(preferenceDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<ResponseObject> setPreference(@RequestParam String campaignId, @RequestBody PreferenceDto preferenceDto){
        preferenceService.setPreference(campaignId, preferenceDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }

    @DeleteMapping
    public ResponseEntity<ResponseObject> deletePreference(@RequestParam String campaignId){
        preferenceService.deletePreference(campaignId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }

}
