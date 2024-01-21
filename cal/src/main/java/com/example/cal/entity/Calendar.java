package com.example.cal.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document
public class Calendar {
    @Id
    private String id;
    @NonNull
    private String campaignId;
    @NonNull
    private LocalDate currentDay;
    @NonNull
    private List<String> events;
}
