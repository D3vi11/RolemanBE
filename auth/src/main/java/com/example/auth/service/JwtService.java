package com.example.auth.service;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey;

    public JwtService(){
        try{
            KeyGenerator generator = KeyGenerator.getInstance("HmacSha512");
            this.secretKey = generator.generateKey();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public String generateToken(String username){
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
        token = token.split(" ")[1];
        try {
            Jwts.parser().decryptWith(secretKey).build().parseSignedClaims(token);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
