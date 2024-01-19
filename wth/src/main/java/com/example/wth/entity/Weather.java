package com.example.wth.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Weather {
    @Id
    private String id;
    @NonNull
    private String campaignId;
    @NonNull
    private List<String> weatherList;
}
