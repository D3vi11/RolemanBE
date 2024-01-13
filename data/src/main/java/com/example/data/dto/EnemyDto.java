package com.example.data.dto;

import com.example.data.CharacterSheet;
import com.example.data.enums.Rarity;
import lombok.Data;

@Data
public class EnemyDto {
    private final String name;
    private final String image;
    private final Rarity rarity;
    private final Integer cr;
    private final String description;
    private final CharacterSheet characterSheet;
}
