package com.example.data.entity;

import com.example.data.CharacterSheet;
import com.example.data.enums.Rarity;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Enemy {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String image;
    @NonNull
    private Rarity rarity;
    @NonNull
    private Integer cr;
    @NonNull
    private Integer xp;
    @NonNull
    private String description;
    @NonNull
    private CharacterSheet characterSheet;
}
