package com.example.wth.service;

import com.example.wth.dto.PreferenceDto;
import com.example.wth.entity.Preference;
import com.example.wth.exception.IdDoesntMatchException;
import com.example.wth.exception.PreferenceAlreadyExistsException;
import com.example.wth.exception.PreferenceNotFoundException;
import com.example.wth.exception.UnableToDeleteException;
import com.example.wth.repository.PreferenceRepository;
import com.mongodb.MongoException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PreferenceService {

    PreferenceRepository preferenceRepository;

    public PreferenceDto readPreference(String campaignId){
        Preference preference = preferenceRepository.findByCampaignId(campaignId)
                .orElseThrow(() -> new PreferenceNotFoundException("Nie znaleziono kampanii"));
        return mapToDto(preference);
    }

    public void setPreference(String campaignId, PreferenceDto preferenceDto){
        if(!campaignId.equals(preferenceDto.getCampaignId())){
            throw new IdDoesntMatchException("Id kampanii są różne");
        }
        Preference preference = preferenceRepository.findByCampaignId(preferenceDto.getCampaignId())
                .orElseThrow(()-> new PreferenceNotFoundException("Nie znaleziono kampanii"));
        preference.setIsExternal(preferenceDto.getIsExternal());
        preferenceRepository.save(preference);
    }

    public void createPreference(PreferenceDto preferenceDto){
        if(preferenceRepository.findByCampaignId(preferenceDto.getCampaignId()).isPresent()){
            throw new PreferenceAlreadyExistsException("Preferencja dla tej kampanii już istnieje");
        }
        preferenceRepository.save(mapToPreference(preferenceDto));
    }

    public void deletePreference(String campaignId){
        try{
            preferenceRepository.deleteByCampaignId(campaignId);
        }catch (MongoException e){
            throw new UnableToDeleteException("Błąd przy usuwaniu preferencji");
        }
    }

    private Preference mapToPreference(PreferenceDto preferenceDto){
        return new Preference(preferenceDto.getCampaignId(),preferenceDto.getIsExternal());
    }
    private PreferenceDto mapToDto(Preference preference){
        return new PreferenceDto(preference.getCampaignId(),preference.getIsExternal());
    }
}
