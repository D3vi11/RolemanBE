package com.example.gen.dto;

import com.example.gen.enums.Difficulty;
import com.example.gen.enums.Rarity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class GeneratorDto {
    @Min(1)
    private final Integer numberOfPlayers;
    @Min(1)
    @Max(20)
    private final Integer teamLevel;
    @Min(1)
    private final Integer numberOfEnemies;
    private final Rarity rarity;
    private final Difficulty difficulty;
}
