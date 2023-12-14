package com.example.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
