package com.example.gen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Difficulty {
    EASY(-1),
    MEDIUM(0),
    HARD(1),
    DEADLY(3);

    private final Integer value;
}
