package com.example.data.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Rule {
    @Id
    String id;
    @NonNull
    String name;
    @NonNull
    String description;
}
