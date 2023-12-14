package com.example.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Entity(name = "USER")
@Table(name = "USER")
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    private Integer ID;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String email;
    @NonNull
    private Boolean isActive;
    @NonNull
    private Integer daysToDeletion;
    @NonNull
    private String confirmationToken;
    @NonNull
    private Instant tokenCreationTime;



}
