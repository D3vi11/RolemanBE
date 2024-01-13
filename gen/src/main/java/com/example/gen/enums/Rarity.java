package com.example.gen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Rarity {
    COMMON("common"),
    UNCOMMON("uncommon"),
    RARE("rare"),
    VERYRARE("veryRare"),
    LEGENDARY("legendary");

    private final String value;

}
