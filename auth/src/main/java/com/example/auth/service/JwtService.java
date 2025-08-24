package com.example.auth.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey = Jwts.SIG.HS512.key().build();

    public String generateToken(String username) {
        long time = System.currentTimeMillis();
        long expiration = time + 100_000_000_000_000l;
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(time))
                .expiration(new Date(expiration))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token){
        token = new AuthToken(token).token();
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
            return true;
        }catch (JwtException e){
            return false;
        }

    }
        public record AuthToken(String token) {
            private static final String header = "Bearer ";

        public AuthToken(String token) {
                if (token.contains(header)) {
                    this.token = token;
                } else {
                    this.token = token.replace(header, "");
                }
            }
        }
}
