package com.example.wth.controller;

import com.example.wth.dto.PreferenceDto;
import com.example.wth.service.PreferenceService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PreferenceController {
    PreferenceService preferenceService;

    @PostMapping("preference")
    public ResponseEntity<String> createPreference(@RequestBody PreferenceDto preferenceDto){
        preferenceService.createPreference(preferenceDto);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jsonObject.toString());
    }

    @PutMapping("preference")
    public ResponseEntity<String> setPreference(@RequestBody PreferenceDto preferenceDto){
        preferenceService.setPreference(preferenceDto);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jsonObject.toString());
    }
}
