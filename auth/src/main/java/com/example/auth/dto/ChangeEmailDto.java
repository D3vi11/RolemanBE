package com.example.auth.dto;

import com.example.auth.validation.annotation.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeEmailDto {
    @NotBlank(message = "Nazwa użytkownika nie może być pusta")
    @Size(min = 5, message = "Nazwa użytkownika musi mieć co najmniej 5 znaków")
    private String username;
    @NotBlank(message = "Hasło nie może być puste")
    @Size(min = 8, message = "Hasło musi mieć co najmniej 8 znaków")
    private String password;
    @NotBlank(message = "Email nie może być pusty")
    @Email(message = "Nieprawidłowy adres email")
    private String newEmail;
}
