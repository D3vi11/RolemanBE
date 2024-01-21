package com.example.mp.entity;

import lombok.Data;
import lombok.NonNull;
@Data
public class Coordinate {
    @NonNull
    private Integer x;
    @NonNull
    private Integer y;
}
