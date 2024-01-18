package com.example.cp.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Campaign {
    @Id
    private String id;
    @NonNull
    private String campaignName;
    @NonNull
    private String gameMasterUsername;
    @NonNull
    private List<String> playersUsernames;
}
