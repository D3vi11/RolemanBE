package com.example.data.service;

import com.example.data.dto.ItemDto;
import com.example.data.dto.SpellDto;
import com.example.data.entity.Item;
import com.example.data.entity.Spell;
import com.example.data.exception.FailedToDeleteException;
import com.example.data.exception.FailedToSaveException;
import com.example.data.exception.NothingFoundException;
import com.example.data.repository.SpellRepository;
import com.mongodb.MongoException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SpellService {
    SpellRepository spellRepository;

    public void saveAll(List<SpellDto> list) {
        try {
            spellRepository.saveAll(mapAll(list));
        } catch (MongoException e) {
            throw new FailedToSaveException("Błąd podczas zapisu danych");
        }
    }

    public Spell findByName(String name) {
        return spellRepository.findByName(name)
                .orElseThrow(() -> new NothingFoundException("Nie znaleziono zaklęcia"));
    }

    public void save(SpellDto spellDto) {
        try {
            spellRepository.save(mapToSpell(spellDto));
        } catch (MongoException e) {
            throw new FailedToSaveException("Błąd podczas zapisu danych");
        }
    }

    public void change(SpellDto spellDto,SpellDto newSpellDto){
        Spell spell = spellRepository.findByName(spellDto.getName())
                .orElseThrow(() -> new NothingFoundException("Nie znaleziono zaklęcia do zmiany"));
        spell.setName(newSpellDto.getName());
        spell.setDescription(newSpellDto.getDescription());
        try{
            spellRepository.save(spell);
        }catch (MongoException e){
            throw new FailedToSaveException("Błąd zapisu");
        }
    }

    public void delete(String name){
        Spell spell = spellRepository.findByName(name)
                .orElseThrow(()->new NothingFoundException("Brak zaklęcia do usunięcia"));
        try {
            spellRepository.delete(spell);
        }catch (MongoException e){
            throw new FailedToDeleteException("Błąd podczas usuwania przedmiotu");
        }
    }

    private Spell mapToSpell(SpellDto spellDto){
        return new Spell(spellDto.getName(),spellDto.getDescription());
    }

    private List<Spell> mapAll(List<SpellDto> spellDtoList) {
        return spellDtoList.stream()
                .map(this::mapToSpell)
                .toList();
    }
}
