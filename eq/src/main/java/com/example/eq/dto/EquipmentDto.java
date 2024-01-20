package com.example.eq.dto;

import com.example.eq.entity.Item;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class EquipmentDto {
    @NonNull
    private String username;
    @NonNull
    private String campaignId;
    @NonNull
    private String characterName;
    @Valid
    @NonNull
    private List<Item> itemList;
}
