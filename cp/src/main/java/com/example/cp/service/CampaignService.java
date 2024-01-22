package com.example.cp.service;

import com.example.cp.dto.CampaignDto;
import com.example.cp.entity.Campaign;
import com.example.cp.enums.AccessEnum;
import com.example.cp.exception.*;
import com.example.cp.repository.CampaignRepository;
import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignService {
    private final CampaignRepository campaignRepository;
    private final RestTemplate restTemplate;

    public ResponseEntity<CampaignDto> getCampaignByNameAndPlayerName(String campaignName, String playerName){
        Campaign campaign = campaignRepository.findByCampaignNameAndUsername(campaignName, playerName)
                .orElseThrow(()-> new CampaignNotFoundException("Nie znaleziono kampanii"));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapToDto(campaign));
    }

    public void createCampaign(CampaignDto campaignDto){
        try {
            Campaign campaign = campaignRepository.save(mapToCampaign(campaignDto));
            restTemplate.postForEntity("http://calendar/propagate?campaignId="+campaign.getId(),null,String.class);
            restTemplate.postForEntity("http://map/propagate?campaignId="+campaign.getId(),null,String.class);
            restTemplate.postForEntity("http://weather/propagate?campaignId="+campaign.getId(),null,String.class);
        }catch (MongoException e){
            throw new UnableToSaveCampaignException("Błąd przy zapisie kampanii");
        }catch (RestClientException e){
            throw new UnableToPropagateException("Błąd podczas propagacji danych");
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
            restTemplate.delete("http://calendar/propagate?campaignId="+campaignId);
            restTemplate.delete("http://map/propagate?campaignId="+campaignId);
            restTemplate.delete("http://weather/propagate?campaignId="+campaignId);
        }catch (MongoException e){
            throw new UnableToDeleteCampaign("Błąd przy usunięciu kampanii");
        }catch (RestClientException e){
            throw new UnableToPropagateException("Błąd podczas propagacji danych");
        }
    }

    public AccessEnum verify(String campaignId, String username){
        boolean isEmpty = campaignRepository.findAllByUsername(username)
                .stream()
                .map(Campaign::getId)
                .filter(id -> id.equals(campaignId))
                .toList()
                .isEmpty();
        if(!isEmpty){
            return AccessEnum.ALLOW;
        }else{
            return AccessEnum.DENY;
        }
    }
    public List<CampaignDto> findAllByUsername(String username){
        return campaignRepository.findAllByUsername(username)
                .stream()
                .map(CampaignService::mapToDto)
                .toList();
    }

    private static CampaignDto mapToDto(Campaign campaign){
        return new CampaignDto(campaign.getId(),campaign.getCampaignName(),campaign.getGameMasterUsername(),campaign.getPlayersUsernames());
    }

    private static Campaign mapToCampaign(CampaignDto campaignDto){
        return new Campaign(campaignDto.getCampaignName(),campaignDto.getGameMasterUsername(),campaignDto.getPlayersUsernames());
    }
}
