package com.example.auth.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
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
        token = token.split(" ")[1];
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
            return true;
        }catch (JwtException e){
            return false;
        }

    }
}
