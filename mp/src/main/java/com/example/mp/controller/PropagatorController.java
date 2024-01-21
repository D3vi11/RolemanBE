package com.example.mp.controller;

import com.example.mp.ResponseObject;
import com.example.mp.service.PropagatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("propagate")
public class PropagatorController {
    private final PropagatorService propagatorService;

    @GetMapping
    public ResponseEntity<ResponseObject> create(@RequestParam String campaignId){
        propagatorService.create(campaignId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }

    @DeleteMapping
    public ResponseEntity<ResponseObject> delete(@RequestParam String campaignId){
        propagatorService.delete(campaignId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }
}
