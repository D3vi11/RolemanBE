package com.example.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterDto {
    @NotBlank(message = "Nazwa użytkownika nie może być pusta")
    private String username;
    @NotBlank(message = "Hasło nie może być puste")
    private String password;
    @NotBlank(message = "Email nie może być pusty")
    @Email(message = "Nieprawidłowy adres email")
    private String email;
}
