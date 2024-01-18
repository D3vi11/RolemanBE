package com.example.cp.controller;

import com.example.cp.dto.CampaignDto;
import com.example.cp.entity.Campaign;
import com.example.cp.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class CampaignController {
    private final CampaignService campaignService;

    @GetMapping
    public ResponseEntity<CampaignDto> getCampaign(@RequestParam String campaignName, @RequestParam String playerName) {
        return campaignService.getCampaignByNameAndPlayerName(campaignName, playerName);
    }
}
