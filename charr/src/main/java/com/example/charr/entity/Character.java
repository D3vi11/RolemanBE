package com.example.charr.entity;

import com.example.charr.enums.ClassEnum;
import com.example.charr.enums.RaceEnum;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Character {
    @Id
    private String id;
    @NonNull
    private String username;
    @NonNull
    private String campaignId;
    @NonNull
    private String name;
    @NonNull
    private RaceEnum race;
    @NonNull
    private ClassEnum characterClass;
    @NonNull
    private Integer strength;
    @NonNull
    private Integer dexterity;
    @NonNull
    private Integer constitution;
    @NonNull
    private Integer intelligence;
    @NonNull
    private Integer wisdom;
    @NonNull
    private Integer charisma;
    @NonNull
    private Integer armorClass;
    @NonNull
    private Integer initiative;
    @NonNull
    private Integer speed;
    @NonNull
    private Integer maxHp;
    @NonNull
    private Integer currentHp;
}
