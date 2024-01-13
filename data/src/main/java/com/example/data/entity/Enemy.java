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
    String id;
    @NonNull
    String name;
    @NonNull
    String image;
    @NonNull
    Rarity rarity;
    @NonNull
    Integer cr;
    @NonNull
    String description;
    @NonNull
    CharacterSheet characterSheet;
}
