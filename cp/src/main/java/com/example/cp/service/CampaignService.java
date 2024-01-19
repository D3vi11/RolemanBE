package com.example.cp.service;

import com.example.cp.dto.CampaignDto;
import com.example.cp.entity.Campaign;
import com.example.cp.exception.CampaignNotFoundException;
import com.example.cp.exception.UnableToDeleteCampaign;
import com.example.cp.exception.UnableToSaveCampaignException;
import com.example.cp.exception.WrongIdException;
import com.example.cp.repository.CampaignRepository;
import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public void createCampaign(CampaignDto campaignDto){
        try {
            campaignRepository.save(mapToCampaign(campaignDto));
        }catch (MongoException e){
            throw new UnableToSaveCampaignException("Błąd przy zapisie kampanii");
        }
    }

    public void modifyCampaign(String campaignId, CampaignDto campaignDto){
        if(!campaignId.equals(campaignDto.getId())){
            throw new WrongIdException("Id kampanii są różne");
        }
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new CampaignNotFoundException("Nie znaleziono kampanii"));
        campaign.setCampaignName(campaignDto.getCampaignName());
        campaign.setGameMasterUsername(campaignDto.getGameMasterUsername());
        campaign.setPlayersUsernames(campaignDto.getPlayersUsernames());
        try {
            campaignRepository.save(campaign);
        }catch (MongoException e){
            throw new UnableToSaveCampaignException("Błąd przy zapisie kampanii");
        }
    }

    public void deleteCampaign(String campaignId){
        campaignRepository.findById(campaignId)
                .orElseThrow(() -> new CampaignNotFoundException("Nie znaleziono kampanii"));
        try {
            campaignRepository.deleteById(campaignId);
        }catch (MongoException e){
            throw new UnableToDeleteCampaign("Błąd przy usunięciu kampanii");
        }
    }

    private CampaignDto mapToDto(Campaign campaign){
        return new CampaignDto(campaign.getId(),campaign.getCampaignName(),campaign.getGameMasterUsername(),campaign.getPlayersUsernames());
    }

    private Campaign mapToCampaign(CampaignDto campaignDto){
        return new Campaign(campaignDto.getCampaignName(),campaignDto.getGameMasterUsername(),campaignDto.getPlayersUsernames());
    }
}
