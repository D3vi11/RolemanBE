package com.example.eq.controller;

import com.example.eq.ResponseObject;
import com.example.eq.dto.EquipmentDto;
import com.example.eq.service.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class EquipmentController {
    private final EquipmentService equipmentService;

    @PutMapping("nextDay")
    public ResponseEntity<ResponseObject> nextDay(@RequestParam String campaignId){
        equipmentService.nextDay(campaignId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }

    @GetMapping
    public ResponseEntity<EquipmentDto> getEquipment(@RequestParam String username, @RequestParam String campaignId, @RequestParam String characterName) {
        EquipmentDto equipmentDto = equipmentService.readEquipment(username, campaignId, characterName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(equipmentDto);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addEquipment(@Valid @RequestBody EquipmentDto equipmentDto) {
        equipmentService.createEquipment(equipmentDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<ResponseObject> updateEquipment(@Valid @RequestBody EquipmentDto equipmentDto) {
        equipmentService.modifyEquipment(equipmentDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }

    @DeleteMapping
    public ResponseEntity<ResponseObject> removeEquipment(@RequestParam String username, @RequestParam String campaignId, @RequestParam String characterName) {
        equipmentService.deleteEquipment(username, campaignId, characterName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK));
    }
}
