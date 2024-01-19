package com.example.charr.service;

import com.example.charr.dto.CharacterDto;
import com.example.charr.entity.Character;
import com.example.charr.exception.NotFoundException;
import com.example.charr.exception.UnableToSaveException;
import com.example.charr.exception.UnableToDeleteException;
import com.example.charr.repository.CharacterRepository;
import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;

    public CharacterDto readCharacter(String characterName, String campaignId, String playerUsername) {
        Character character = characterRepository.findByNameAndCampaignIdAndUsername(characterName, campaignId, playerUsername)
                .orElseThrow(() -> new NotFoundException("Nie znaleziono postaci"));
        return mapToDto(character);
    }

    public void createCharacter(CharacterDto characterDto) {
        if (characterRepository.findByNameAndCampaignIdAndUsername(characterDto.getName(), characterDto.getCampaignId(), characterDto.getUsername()).isPresent()) {
            throw new UnableToSaveException("Taka postać już istnieje");
        }
        try {
            characterRepository.save(mapToCharacter(characterDto));
        } catch (MongoException e) {
            throw new UnableToSaveException("Błąd podczas zapisu do bazy danych");
        }
    }

    public void modifyCharacter(CharacterDto characterDto) {
        Character character = characterRepository.findByNameAndCampaignIdAndUsername(characterDto.getName(), characterDto.getCampaignId(), characterDto.getUsername())
                .orElseThrow(() -> new NotFoundException("Nie znaleziono postaci"));
        character.setStrength(characterDto.getStrength());
        character.setDexterity(characterDto.getDexterity());
        character.setConstitution(characterDto.getConstitution());
        character.setIntelligence(characterDto.getIntelligence());
        character.setWisdom(characterDto.getWisdom());
        character.setCharisma(characterDto.getCharisma());
        character.setArmorClass(characterDto.getArmorClass());
        character.setInitiative(characterDto.getInitiative());
        character.setSpeed(characterDto.getSpeed());
        character.setMaxHp(characterDto.getMaxHp());
        character.setCurrentHp(characterDto.getCurrentHp());
        try{
            characterRepository.save(character);
        }catch (MongoException e){
            throw new UnableToSaveException("Błąd podczas zapisu");
        }

    }

    public void deleteCharacter(String characterName, String campaignId, String playerUsername){
        try {
            characterRepository.deleteByNameAndCampaignIdAndUsername(characterName,campaignId,playerUsername);
        }catch (MongoException e){
            throw new UnableToDeleteException("Błąd podczas usuwania");
        }
    }

    private Character mapToCharacter(CharacterDto characterDto) {
        return new Character(characterDto.getUsername()
                , characterDto.getCampaignId()
                , characterDto.getName()
                , characterDto.getRace()
                , characterDto.getCharacterClass()
                , characterDto.getStrength()
                , characterDto.getDexterity()
                , characterDto.getConstitution()
                , characterDto.getIntelligence()
                , characterDto.getWisdom()
                , characterDto.getCharisma()
                , characterDto.getArmorClass()
                , characterDto.getInitiative()
                , characterDto.getSpeed()
                , characterDto.getMaxHp()
                , characterDto.getCurrentHp());
    }

    private CharacterDto mapToDto(Character character) {
        return new CharacterDto(character.getUsername()
                , character.getCampaignId()
                , character.getName()
                , character.getRace()
                , character.getCharacterClass()
                , character.getStrength()
                , character.getDexterity()
                , character.getConstitution()
                , character.getIntelligence()
                , character.getWisdom()
                , character.getCharisma()
                , character.getArmorClass()
                , character.getInitiative()
                , character.getSpeed()
                , character.getMaxHp()
                , character.getCurrentHp());
    }
}
