package com.example.data.service;

import com.example.data.dto.EnemyDto;
import com.example.data.entity.Enemy;
import com.example.data.enums.Rarity;
import com.example.data.exception.FailedToDeleteException;
import com.example.data.exception.FailedToSaveException;
import com.example.data.exception.NothingFoundException;
import com.example.data.repository.EnemyRepository;
import com.mongodb.MongoException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EnemyService {
    EnemyRepository enemyRepository;

    public void saveAll(List<EnemyDto> list) {
        try {
            enemyRepository.saveAll(mapAll(list));
        } catch (MongoException e) {
            throw new FailedToSaveException("Błąd podczas zapisu danych");
        }
    }

    public Enemy findByName(String name) {
        return enemyRepository.findByName(name)
                .orElseThrow(() -> new NothingFoundException("Nie znaleziono przeciwnika"));
    }

    public List<EnemyDto> findAllByRarityAndCr(Rarity rarity, Integer cr, Integer limit){
        return mapAlltoDto(enemyRepository.findAllByRarityAndCr(rarity, cr))
                .stream()
                .limit(limit)
                .toList();
    }

    public void save(EnemyDto enemyDto) {
        try {
            enemyRepository.save(mapToEnemy(enemyDto));
        } catch (MongoException e) {
            throw new FailedToSaveException("Błąd podczas zapisu danych");
        }
    }

    public void change(String name, EnemyDto newEnemyDto) {
        Enemy enemy = enemyRepository.findByName(name)
                .orElseThrow(() -> new NothingFoundException("Nie znaleziono przeciwnika do zmiany"));
        enemy.setName(newEnemyDto.getName());
        enemy.setImage(newEnemyDto.getImage());
        enemy.setRarity(newEnemyDto.getRarity());
        enemy.setCr(newEnemyDto.getCr());
        enemy.setDescription(newEnemyDto.getDescription());
        enemy.setCharacterSheet(newEnemyDto.getCharacterSheet());
        try {
            enemyRepository.save(enemy);
        } catch (MongoException e) {
            throw new FailedToSaveException("Błąd zapisu");
        }
    }

    public void delete(String name) {
        Enemy enemy = enemyRepository.findByName(name)
                .orElseThrow(() -> new NothingFoundException("Brak postaci do usunięcia"));
        try {
            enemyRepository.delete(enemy);
        } catch (MongoException e) {
            throw new FailedToDeleteException("Błąd podczas usuwania postaci");
        }
    }

    private Enemy mapToEnemy(EnemyDto enemyDto) {
        return new Enemy(enemyDto.getName(), enemyDto.getImage(), enemyDto.getRarity(), enemyDto.getCr(), enemyDto.getDescription(), enemyDto.getCharacterSheet());
    }
    private EnemyDto mapToDto(Enemy enemy){
        return new EnemyDto(enemy.getName(), enemy.getImage(),enemy.getRarity(),enemy.getCr(),enemy.getDescription(),enemy.getCharacterSheet());
    }

    private List<EnemyDto> mapAlltoDto(List<Enemy> list) {
        return list.stream()
                .map(this::mapToDto)
                .toList();
    }

    private List<Enemy> mapAll(List<EnemyDto> list) {
        return list.stream()
                .map(this::mapToEnemy)
                .toList();
    }
}
