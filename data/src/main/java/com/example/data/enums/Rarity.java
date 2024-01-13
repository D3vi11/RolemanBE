package com.example.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rarity {
    COMMON("common"),
    UNCOMMON("uncommon"),
    RARE("rare"),
    VERYRARE("veryRare"),
    LEGENDARY("legendary");

    private final String value;
}
