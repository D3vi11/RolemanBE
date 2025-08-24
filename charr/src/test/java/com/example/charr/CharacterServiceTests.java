package com.example.charr;

import com.example.charr.dto.CharacterDto;
import com.example.charr.entity.Character;
import com.example.charr.enums.ClassEnum;
import com.example.charr.enums.RaceEnum;
import com.example.charr.exception.NotFoundException;
import com.example.charr.exception.UnableToSaveException;
import com.example.charr.repository.CharacterRepository;
import com.example.charr.service.CharacterService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTests {

    @Mock
    CharacterRepository characterRepository;

    @InjectMocks
    CharacterService characterService;

    private static final String characterName = "name";
    private static final String campaignId = "campaignId";
    private static final String playerUsername = "campaignId";

    @Nested
    public class readCharacterTests {
        private static final Character character = makeCharacter();

        @Test
        public void readCharacterTest() {
            when(characterRepository.findByNameAndCampaignIdAndUsername(characterName, campaignId, playerUsername)).thenReturn(Optional.of(character));
            CharacterDto characterDto = characterService.readCharacter(characterName, campaignId, playerUsername);
            assertEquals(playerUsername, characterDto.getUsername());
            assertEquals(campaignId, characterDto.getCampaignId());
            assertEquals(characterName, characterDto.getName());
        }

        @Test
        public void characterNotFoundTest() {
            when(characterRepository.findByNameAndCampaignIdAndUsername(characterName, campaignId, playerUsername)).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> characterService.readCharacter(characterName, campaignId, playerUsername));
        }
    }

    @Nested
    public class CreateCharacterTests {
        private static final Character character = makeCharacter();
        private static final CharacterDto characterDto = makeCharacterDto();

        @Test
        public void createCharacterTest() {
            when(characterRepository
                    .findByNameAndCampaignIdAndUsername(characterDto.getName(), characterDto.getCampaignId(), characterDto.getUsername()))
                    .thenReturn(Optional.empty());
            characterService.createCharacter(characterDto);
            ArgumentCaptor<Character> characterCaptor = ArgumentCaptor.forClass(Character.class);
            verify(characterRepository).save(characterCaptor.capture());
            assertEquals(characterDto.getName(), characterCaptor.getValue().getName());
            assertEquals(characterDto.getCampaignId(), characterCaptor.getValue().getCampaignId());
            assertEquals(characterDto.getUsername(), characterCaptor.getValue().getUsername());
        }

        @Test
        public void characterExistsTest(){
            when(characterRepository
                    .findByNameAndCampaignIdAndUsername(characterDto.getName(), characterDto.getCampaignId(), characterDto.getUsername()))
                    .thenReturn(Optional.of(character));
            assertThrows(UnableToSaveException.class, () -> characterService.createCharacter(characterDto));
            ArgumentCaptor<Character> characterCaptor = ArgumentCaptor.forClass(Character.class);
            verify(characterRepository, never()).save(characterCaptor.capture());
        }
    }

    @Nested
    public class ModifyCharacterTests{
        private static final Character character = makeCharacter();
        private static final CharacterDto characterDto = makeCharacterDto();

        @Test
        public void modifyCharacterTest(){
            characterDto.setStrength(10);
            when(characterRepository
                    .findByNameAndCampaignIdAndUsername(characterDto.getName(), characterDto.getCampaignId(), characterDto.getUsername()))
                    .thenReturn(Optional.of(character));
            characterService.modifyCharacter(characterDto);
            ArgumentCaptor<Character> characterCaptor = ArgumentCaptor.forClass(Character.class);
            verify(characterRepository).save(characterCaptor.capture());
            assertEquals(characterDto.getStrength(), characterCaptor.getValue().getStrength());
        }

        @Test
        public void characterNotFoundTest(){
            when(characterRepository
                    .findByNameAndCampaignIdAndUsername(characterDto.getName(), characterDto.getCampaignId(), characterDto.getUsername()))
                    .thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> characterService.modifyCharacter(characterDto));
            ArgumentCaptor<Character> characterCaptor = ArgumentCaptor.forClass(Character.class);
            verify(characterRepository, never()).save(characterCaptor.capture());
        }
    }

    @Nested
    public class DeleteCharacterTests{

        @Test
        public void deleteCharacterTest(){
            characterService.deleteCharacter(characterName,campaignId, playerUsername);
            verify(characterRepository).deleteByNameAndCampaignIdAndUsername(characterName, campaignId, playerUsername);
        }
    }

    @Nested
    public class GetAllNamesTests{

        @Test
        public void getAllNamesTest(){
            when(characterRepository.findAllByCampaignIdAndUsername(campaignId, playerUsername)).thenReturn(List.of(makeCharacter(), makeCharacter(), makeCharacter()));
            List<String> names = characterService.getAllNames(campaignId, playerUsername);
            assertEquals(3, names.size());
            assertEquals(characterName, names.get(0));
            assertEquals(characterName, names.get(1));
            assertEquals(characterName, names.get(2));
        }
    }

    private static Character makeCharacter() {
        return new Character(
                playerUsername,
                campaignId,
                characterName,
                RaceEnum.ELF,
                ClassEnum.BARBARIAN,
                1,
                1,
                1,
                1,
                1,
                1,
                1,
                1,
                1,
                1,
                1);
    }

    private static CharacterDto makeCharacterDto() {
        return new CharacterDto(
                playerUsername,
                campaignId,
                characterName,
                RaceEnum.ELF,
                ClassEnum.BARBARIAN,
                1,
                1,
                1,
                1,
                1,
                1,
                1,
                1,
                1,
                1,
                1);
    }


}
