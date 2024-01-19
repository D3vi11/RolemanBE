package com.example.charr.dto;

import com.example.charr.enums.ClassEnum;
import com.example.charr.enums.RaceEnum;
import lombok.Data;
import lombok.NonNull;
@Data
public class CharacterDto {
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
