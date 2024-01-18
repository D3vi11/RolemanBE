package com.example.cp.service;

import com.example.cp.dto.CampaignDto;
import com.example.cp.entity.Campaign;
import com.example.cp.exception.CampaignNotFoundException;
import com.example.cp.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CampaignService {
    private final CampaignRepository campaignRepository;

    public ResponseEntity<CampaignDto> getCampaignByNameAndPlayerName(String campaignName, String playerName){
        Campaign campaign = campaignRepository.findByCampaignNameAndUsername(campaignName, playerName)
                .orElseThrow(()-> new CampaignNotFoundException("Nie znaleziono kampanii"));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapToDto(campaign));
    }

    private CampaignDto mapToDto(Campaign campaign){
        return new CampaignDto(campaign.getId(),campaign.getCampaignName(),campaign.getGameMasterUsername(),campaign.getPlayersUsernames());
    }
}
