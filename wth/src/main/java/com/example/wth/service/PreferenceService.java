package com.example.wth.service;

import com.example.wth.dto.PreferenceDto;
import com.example.wth.entity.Preference;
import com.example.wth.exception.CampaignNotFoundException;
import com.example.wth.repository.PreferenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PreferenceService {

    PreferenceRepository preferenceRepository;

    public void setPreference(PreferenceDto preferenceDto){
        Preference preference = preferenceRepository.findByCampaignId(preferenceDto.getCampaignId())
                .orElseThrow(CampaignNotFoundException::new);
        preference.setIsExternal(preferenceDto.getIsExternal());
        preferenceRepository.save(preference);
    }
}
