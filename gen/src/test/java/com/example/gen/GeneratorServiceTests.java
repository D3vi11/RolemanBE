package com.example.gen;

import com.example.gen.dto.GeneratorDto;
import com.example.gen.enums.Difficulty;
import com.example.gen.enums.Rarity;
import com.example.gen.exception.EnemiesNotFoundException;
import com.example.gen.service.GeneratorService;
import com.google.gson.JsonParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeneratorServiceTests {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    GeneratorService generatorService;

    private final Integer numberOfPlayers = 4;
    private final Integer teamLevel = 2;
    private final Integer numberOfEnemies = 1;
    private final Rarity rarity = Rarity.COMMON;
    private final Difficulty difficulty = Difficulty.EASY;

    private final GeneratorDto generatorDto = new GeneratorDto(numberOfPlayers, teamLevel, numberOfEnemies, rarity, difficulty);

    String enemy = "[{\"name\":\"enemy\"}]";
    String body = "[{" + enemy.replaceAll("\\[\\{", "").replaceAll("}]", "") + ",\"number\":1}]";

    @Test
    public void generateTest() {
        when(restTemplate.getForEntity("http://data/enemies/enemies?rarity=" + rarity + "&cr=" + 1 + "&limit=" + numberOfEnemies, String.class))
                .thenReturn(ResponseEntity.of(Optional.of(enemy)));
        String text = generatorService.generate(generatorDto);
        assertEquals(body, text);
    }

    @Test
    public void enemiesNotFoundTest() {
        when(restTemplate.getForEntity("http://data/enemies/enemies?rarity=" + rarity + "&cr=" + 1 + "&limit=" + numberOfEnemies, String.class))
                .thenReturn(ResponseEntity.of(Optional.empty()));
        assertThrows(EnemiesNotFoundException.class, () -> generatorService.generate(generatorDto));
    }

    @Test
    public void arrayIsEmptyTest() {
        String incorrectString = "[]";
        when(restTemplate.getForEntity("http://data/enemies/enemies?rarity=" + rarity + "&cr=" + 1 + "&limit=" + numberOfEnemies, String.class))
                .thenReturn(ResponseEntity.of(Optional.of(incorrectString)));
        assertThrows(EnemiesNotFoundException.class, () -> generatorService.generate(generatorDto));
    }

    @Test
    public void notAnArrayTest() {
        String incorrectString = "{}";
        when(restTemplate.getForEntity("http://data/enemies/enemies?rarity=" + rarity + "&cr=" + 1 + "&limit=" + numberOfEnemies, String.class))
                .thenReturn(ResponseEntity.of(Optional.of(incorrectString)));
        assertThrows(IllegalStateException.class, () -> generatorService.generate(generatorDto));
    }

    @Test
    public void tableWithoutAnObjectTest() {
        String incorrectString = "[a,b]";
        when(restTemplate.getForEntity("http://data/enemies/enemies?rarity=" + rarity + "&cr=" + 1 + "&limit=" + numberOfEnemies, String.class))
                .thenReturn(ResponseEntity.of(Optional.of(incorrectString)));
        assertThrows(JsonParseException.class, () -> generatorService.generate(generatorDto));
    }
}
