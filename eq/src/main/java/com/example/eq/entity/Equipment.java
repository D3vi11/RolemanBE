package com.example.eq.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Equipment {
    @Id
    private String id;
    @NonNull
    private String username;
    @NonNull
    private String campaignId;
    @NonNull
    private String characterName;
    @NonNull
    private List<Item> itemList;
}
