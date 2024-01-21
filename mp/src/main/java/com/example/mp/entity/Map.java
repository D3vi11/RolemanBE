package com.example.mp.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Map {
    @Id
    private String id;
    @NonNull
    private String campaignId;
    @NonNull
    private String imageUrl;
    @NonNull
    private Coordinate mapSize;
    @NonNull
    private Coordinate currentLocation;
}
