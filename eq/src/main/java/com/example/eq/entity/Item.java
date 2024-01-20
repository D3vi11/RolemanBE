package com.example.eq.entity;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NonNull;

@Data
public class Item {
    @NonNull
    private String name;
    private String damage;
    private Integer daysToExpire;
    @NonNull
    private String description;
    @Min(1)
    @NonNull
    private Integer quantity;
}
