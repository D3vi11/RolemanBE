package com.example.cp;

import com.example.cp.dto.CampaignDto;
import com.example.cp.entity.Campaign;
import com.example.cp.enums.AccessEnum;
import com.example.cp.exception.CampaignNotFoundException;
import com.example.cp.exception.UnableToPropagateException;
import com.example.cp.exception.UnableToSaveCampaignException;
import com.example.cp.exception.WrongIdException;
import com.example.cp.repository.CampaignRepository;
import com.example.cp.service.CampaignService;
import com.mongodb.MongoException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTests {

    @Mock
    CampaignRepository campaignRepository;
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    CampaignService campaignService;

    private final static String campaignName = "campaignName";
    private final static String playerName = "playerName";
    private final static String gmName = "gmName";
    private final static String campaignId = "1";

    @Nested
    public class GetCampaignTests{

        private final static Campaign campaign = new Campaign(campaignName, gmName, List.of(playerName));

        @Test
        public void getCampaignTest(){
            when(campaignRepository.findByCampaignNameAndUsername(campaignName, playerName)).thenReturn(Optional.of(campaign));
            CampaignDto campaignDto = campaignService.getCampaign(campaignName, playerName);
            assertEquals(campaignDto.getCampaignName(), campaign.getCampaignName());
            assertEquals(campaignDto.getPlayersUsernames(), campaign.getPlayersUsernames());
            assertEquals(campaignDto.getGameMasterUsername(), campaign.getGameMasterUsername());
        }

        @Test
        public void campaignNotFoundTest(){
            when(campaignRepository.findByCampaignNameAndUsername(campaignName, playerName)).thenReturn(Optional.empty());
            assertThrows(CampaignNotFoundException.class, () -> campaignService.getCampaign(campaignName, playerName));
        }
    }

    @Nested
    public class CreateCampaignTests{

        private final static CampaignDto campaignDto = new CampaignDto(campaignId, campaignName, gmName, List.of(playerName));
        private final static Campaign campaign = new Campaign(campaignName, gmName, List.of(playerName));

        @Test
        public void createCampaignTest(){
            when(campaignRepository.save(campaign)).thenReturn(campaign);
            campaignService.createCampaign(campaignDto);
            ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
            verify(restTemplate, times(3)).postForEntity(stringCaptor.capture(),eq(null), eq(String.class));
            assertEquals("http://calendar/propagate?campaignId", stringCaptor.getAllValues().get(0).split("=")[0]);
            assertEquals("http://map/propagate?campaignId", stringCaptor.getAllValues().get(1).split("=")[0]);
            assertEquals("http://weather/propagate?campaignId", stringCaptor.getAllValues().get(2).split("=")[0]);
        }

        @Test
        public void databaseErrorTest(){
            when(campaignRepository.save(campaign)).thenThrow(new MongoException(""));
            assertThrows(UnableToSaveCampaignException.class, () -> campaignService.createCampaign(campaignDto));
            ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
            verify(restTemplate, never()).postForEntity(stringCaptor.capture(),eq(null), eq(String.class));
        }

        @Test
        public void propagationErrorTest(){
            when(campaignRepository.save(campaign)).thenReturn(campaign);
            when(restTemplate.postForEntity("http://calendar/propagate?campaignId="+campaign.getId(),null,String.class)).thenThrow(new RestClientException(""));
            assertThrows(UnableToPropagateException.class, () -> campaignService.createCampaign(campaignDto));
            ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
            verify(restTemplate, times(1)).postForEntity(stringCaptor.capture(),eq(null), eq(String.class));
        }
    }

    @Nested
    public class ModifyCampaignTests{
        private final static CampaignDto campaignDto = new CampaignDto(campaignId, campaignName, gmName, List.of(playerName));
        private final static Campaign campaign = new Campaign(campaignName, gmName, List.of(playerName));

        @Test
        public void modifyCampaignTest(){
            when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaign));
            campaignService.modifyCampaign(campaignId, campaignDto);
            ArgumentCaptor<Campaign> campaignCaptor = ArgumentCaptor.forClass(Campaign.class);
            verify(campaignRepository).save(campaignCaptor.capture());
            assertEquals(campaignDto.getCampaignName(), campaignCaptor.getValue().getCampaignName());
            assertEquals(campaignDto.getPlayersUsernames(), campaignCaptor.getValue().getPlayersUsernames());
            assertEquals(campaignDto.getGameMasterUsername(), campaignCaptor.getValue().getGameMasterUsername());
        }

        @Test
        public void differentIdTest(){
            assertThrows(WrongIdException.class, () -> campaignService.modifyCampaign("2", campaignDto));
            ArgumentCaptor<Campaign> campaignCaptor = ArgumentCaptor.forClass(Campaign.class);
            verify(campaignRepository, never()).save(campaignCaptor.capture());
        }

        @Test
        public void campaignNotFoundTest(){
            when(campaignRepository.findById(campaignId)).thenReturn(Optional.empty());
            assertThrows(CampaignNotFoundException.class, () -> campaignService.modifyCampaign(campaignId, campaignDto));
            ArgumentCaptor<Campaign> campaignCaptor = ArgumentCaptor.forClass(Campaign.class);
            verify(campaignRepository, never()).save(campaignCaptor.capture());
        }

        @Test
        public void databaseErrorTest(){
            when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaign));
            when(campaignRepository.save(campaign)).thenThrow(new MongoException(""));
            assertThrows(UnableToSaveCampaignException.class, () -> campaignService.modifyCampaign(campaignId, campaignDto));
            ArgumentCaptor<Campaign> campaignCaptor = ArgumentCaptor.forClass(Campaign.class);
            verify(campaignRepository, times(1)).save(campaignCaptor.capture());
        }
    }

    @Nested
    public class DeleteCampaignTests{
        private final static CampaignDto campaignDto = new CampaignDto(campaignId, campaignName, gmName, List.of(playerName));
        private final static Campaign campaign = new Campaign(campaignName, gmName, List.of(playerName));

        @Test
        public void deleteCampaignTest(){
            when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaign));
            campaignService.deleteCampaign(campaignId);
            ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
            verify(campaignRepository).deleteById(stringCaptor.capture());
            verify(restTemplate, times(3)).delete(stringCaptor.capture());
            assertEquals(campaignId, stringCaptor.getAllValues().get(0));
            assertEquals("http://calendar/propagate?campaignId", stringCaptor.getAllValues().get(1).split("=")[0]);
            assertEquals("http://map/propagate?campaignId", stringCaptor.getAllValues().get(2).split("=")[0]);
            assertEquals("http://weather/propagate?campaignId", stringCaptor.getAllValues().get(3).split("=")[0]);
        }

        @Test
        public void campaignNotFoundTest(){
            when(campaignRepository.findById(campaignId)).thenReturn(Optional.empty());
            assertThrows(CampaignNotFoundException.class, () -> campaignService.deleteCampaign(campaignId));
            ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
            verify(campaignRepository, never()).deleteById(stringCaptor.capture());
            verify(restTemplate, never()).delete(stringCaptor.capture());
        }
    }

    @Nested
    public class VerifyTests{

        private final static Campaign campaign = new Campaign(campaignName, gmName, List.of(playerName));

        @Test
        public void verifyAllowTest(){
            when(campaignRepository.findAllByUsername(playerName)).thenReturn(List.of(campaign));
            campaign.setId(campaignId);
            AccessEnum accessEnum = campaignService.verify(campaignId, playerName);
            assertEquals(accessEnum, AccessEnum.ALLOW);
        }

        @Test
        public void verifyDenyTest(){
            when(campaignRepository.findAllByUsername(playerName)).thenReturn(List.of(campaign));
            campaign.setId("2");
            AccessEnum accessEnum = campaignService.verify(campaignId, playerName);
            assertEquals(accessEnum, AccessEnum.DENY);
        }
    }

    @Nested
    public class FindAllByUsernameTests{

        private final static CampaignDto campaignDto = new CampaignDto(campaignId, campaignName, gmName, List.of(playerName));
        private final static Campaign campaign = new Campaign(campaignName, gmName, List.of(playerName));

        @Test
        public void findAllByUsernameTest(){
            campaign.setId(campaignId);
            when(campaignRepository.findAllByUsername(playerName)).thenReturn(List.of(campaign));
            List<CampaignDto> list = campaignService.findAllByUsername(playerName);
            assertEquals(list, List.of(campaignDto));
        }
    }
}
