package com.example.wth.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Preference {
    @Id
    private String id;
    @NonNull
    private Integer campaignId;
    @NonNull
    private Boolean isExternal;
}
