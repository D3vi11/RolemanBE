package com.example.auth;

import com.example.auth.service.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JwtServiceTests {

    private final JwtService jwtService = new JwtService();
    private String token;

    @BeforeEach
    public void prepare() {
        token = jwtService.generateToken("abc");
    }

    @Test
    public void validateCorrectToken() {
        boolean result = jwtService.validateToken(token);
        Assertions.assertTrue(result);
    }

    @Test
    public void validateIncorrectToken() {
        boolean result = jwtService.validateToken(token + "abc");
        Assertions.assertFalse(result);
    }
}
