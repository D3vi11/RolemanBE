package com.example.wth;

import com.example.wth.dto.PreferenceDto;
import com.example.wth.entity.Preference;
import com.example.wth.exception.IdDoesntMatchException;
import com.example.wth.exception.PreferenceAlreadyExistsException;
import com.example.wth.exception.PreferenceNotFoundException;
import com.example.wth.exception.UnableToDeleteException;
import com.example.wth.repository.PreferenceRepository;
import com.example.wth.service.PreferenceService;
import com.mongodb.MongoException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PreferenceServiceTests {
    @Mock
    PreferenceRepository preferenceRepository;

    @InjectMocks
    PreferenceService preferenceService;

    String campaignId = "campaignId";

    @Nested
    public class ReadPreferenceTests {
        Preference preference = new Preference(campaignId, false);
        PreferenceDto preferenceDto = new PreferenceDto(campaignId, false);

        @Test
        public void readPreferenceTest() {
            when(preferenceRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(preference));
            PreferenceDto dto = preferenceService.readPreference(campaignId);
            assertEquals(preferenceDto, dto);
        }

        @Test
        public void preferenceNotFoundTest() {
            when(preferenceRepository.findByCampaignId(campaignId)).thenReturn(Optional.empty());
            assertThrows(PreferenceNotFoundException.class, () -> preferenceService.readPreference(campaignId));
        }
    }

    @Nested
    public class SetPreferenceTests {
        Preference preference = new Preference(campaignId, false);
        PreferenceDto preferenceDto = new PreferenceDto(campaignId, false);
        PreferenceDto preferenceDto2 = new PreferenceDto(campaignId, true);

        @Test
        public void setPreferenceTest() {
            when(preferenceRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(preference));
            preferenceService.setPreference(campaignId, preferenceDto2);
            ArgumentCaptor<Preference> preferenceCaptor = ArgumentCaptor.forClass(Preference.class);
            verify(preferenceRepository).save(preferenceCaptor.capture());
            assertEquals(preferenceDto2.getIsExternal(), preferenceCaptor.getValue().getIsExternal());
        }

        @Test
        public void campaignIdNotMatchingTest() {
            assertThrows(IdDoesntMatchException.class, () -> preferenceService.setPreference(campaignId + "1", preferenceDto));
            ArgumentCaptor<Preference> preferenceCaptor = ArgumentCaptor.forClass(Preference.class);
            verify(preferenceRepository, never()).findByCampaignId(campaignId);
            verify(preferenceRepository, never()).save(preferenceCaptor.capture());
        }

        @Test
        public void preferenceNotFoundTest() {
            when(preferenceRepository.findByCampaignId(campaignId)).thenReturn(Optional.empty());
            assertThrows(PreferenceNotFoundException.class, () -> preferenceService.setPreference(campaignId, preferenceDto));
            ArgumentCaptor<Preference> preferenceCaptor = ArgumentCaptor.forClass(Preference.class);
            verify(preferenceRepository, never()).save(preferenceCaptor.capture());
        }
    }

    @Nested
    public class CreatePreferenceTests {
        Preference preference = new Preference(campaignId, false);
        PreferenceDto preferenceDto = new PreferenceDto(campaignId, false);

        @Test
        public void createPreferenceTest() {
            when(preferenceRepository.findByCampaignId(campaignId)).thenReturn(Optional.empty());
            preferenceService.createPreference(preferenceDto);
            verify(preferenceRepository).save(preference);
        }

        @Test
        public void preferenceExistsTest() {
            when(preferenceRepository.findByCampaignId(campaignId)).thenReturn(Optional.of(preference));
            assertThrows(PreferenceAlreadyExistsException.class, () -> preferenceService.createPreference(preferenceDto));
            verify(preferenceRepository, never()).save(preference);
        }
    }

    @Nested
    public class DeletePreferenceTests {

        @Test
        public void deletePreferenceTest() {
            preferenceService.deletePreference(campaignId);
            verify(preferenceRepository).deleteByCampaignId(campaignId);
        }

        @Test
        public void databaseErrorTest() {
            doThrow(new MongoException("")).when(preferenceRepository).deleteByCampaignId(campaignId);
            assertThrows(UnableToDeleteException.class, () -> preferenceService.deletePreference(campaignId));
        }
    }

}
