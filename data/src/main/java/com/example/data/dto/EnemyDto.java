package com.example.data.dto;

import com.example.data.CharacterSheet;
import com.example.data.enums.Rarity;
import lombok.Data;
import lombok.NonNull;

@Data
public class EnemyDto {
    private final String name;
    private final String image;
    private final Rarity rarity;
    private final Integer cr;
    private final Integer xp;
    private final String description;
    private final CharacterSheet characterSheet;
}
