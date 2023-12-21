package com.example.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Document
public class Items {

    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String requirements;
    @NonNull
    private String description;
}
