package com.example.cp.controller;

import com.example.cp.ResponseObject;
import com.example.cp.dto.CampaignDto;
import com.example.cp.enums.AccessEnum;
import com.example.cp.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class CampaignController {
    private final CampaignService campaignService;

    @GetMapping("all")
    public ResponseEntity<List<CampaignDto>> getAllCampaigns(@RequestParam String username){
        List<CampaignDto> campaigns = campaignService.findAllByUsername(username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(campaigns);
    }

    @GetMapping
    public ResponseEntity<CampaignDto> getCampaign(@RequestParam String campaignName, @RequestParam String username) {
        return campaignService.getCampaignByNameAndPlayerName(campaignName, username);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> postCampaign(@RequestBody CampaignDto campaignDto){
        campaignService.createCampaign(campaignDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject("Utworzono kampanię", HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<ResponseObject> putCampaign(@RequestParam String campaignId, @RequestBody CampaignDto campaignDto){
        campaignService.modifyCampaign(campaignId,campaignDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject("Kampania została zmodyfikowana", HttpStatus.OK));
    }

    @DeleteMapping
    public ResponseEntity<ResponseObject> deleteCampaign(@RequestParam String campaignId){
        campaignService.deleteCampaign(campaignId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject("Kampania została usunięta",HttpStatus.OK));
    }

    @GetMapping("verify")
    public ResponseEntity<ResponseObject> verify(@RequestParam String campaignId, @RequestParam String username){
        AccessEnum verified = campaignService.verify(campaignId, username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject(verified.toString(),HttpStatus.OK));
    }
}
