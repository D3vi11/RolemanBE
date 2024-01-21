package com.example.data;

import lombok.Data;
import lombok.NonNull;

@Data
public class CharacterSheet {
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
    private Integer hp;
    @NonNull
    private String damage;

}
