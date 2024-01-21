package com.example.mp.dto;

import com.example.mp.entity.Coordinate;
import lombok.Data;
import lombok.NonNull;

@Data
public class MapDto {
    @NonNull
    private String campaignId;
    @NonNull
    private String imageUrl;
    @NonNull
    private Coordinate mapSize;
    @NonNull
    private Coordinate currentLocation;
}
