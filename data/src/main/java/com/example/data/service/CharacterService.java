package com.example.data.service;

import com.example.data.dto.CharacterDto;
import com.example.data.entity.Character;
import com.example.data.entity.Item;
import com.example.data.exception.FailedToDeleteException;
import com.example.data.exception.FailedToSaveException;
import com.example.data.exception.NothingFoundException;
import com.example.data.repository.CharacterRepository;
import com.mongodb.MongoException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class CharacterService {
    CharacterRepository characterRepository;
    public void saveAll(List<CharacterDto> list) {
        try {
            characterRepository.saveAll(mapAll(list));
        } catch (MongoException e) {
            throw new FailedToSaveException("Błąd podczas zapisu danych");
        }
    }

    public Character findByName(String name) {
        return characterRepository.findByName(name)
                .orElseThrow(() -> new NothingFoundException("Nie znaleziono postaci"));
    }

    public void save(CharacterDto characterDto) {
        try {
            characterRepository.save(mapToCharacter(characterDto));
        } catch (MongoException e) {
            throw new FailedToSaveException("Błąd podczas zapisu danych");
        }
    }

    public void change(CharacterDto characterDto,CharacterDto newCharacterDto){
        Character character = characterRepository.findByName(characterDto.getName())
                .orElseThrow(() -> new NothingFoundException("Nie znaleziono postaci do zmiany"));
        character.setName(newCharacterDto.getName());
        character.setDescription(newCharacterDto.getDescription());
        try{
            characterRepository.save(character);
        }catch (MongoException e){
            throw new FailedToSaveException("Błąd zapisu");
        }
    }

    public void delete(String name){
        Character character = characterRepository.findByName(name)
                .orElseThrow(()->new NothingFoundException("Brak przedmiotu do usunięcia"));
        try {
            characterRepository.delete(character);
        }catch (MongoException e){
            throw new FailedToDeleteException("Błąd podczas usuwania przedmiotu");
        }
    }

    private Character mapToCharacter(CharacterDto characterDto) {
        return new Character(characterDto.getName(), characterDto.getDescription());
    }

    private List<Character> mapAll(List<CharacterDto> list) {
        return list.stream()
                .map(this::mapToCharacter)
                .toList();
    }
}
