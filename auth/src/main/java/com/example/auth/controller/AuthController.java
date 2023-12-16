package com.example.auth.controller;

import com.example.auth.dto.LoginDto;
import com.example.auth.dto.RegisterDto;
import com.example.auth.service.JwtService;
import com.example.auth.service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class AuthController {

    private UserService userService;
    private JwtService jwtService;

    @PostMapping("login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        String token = userService.loginUser(loginDto);
        headers.put("Authorization", List.of("Bearer "+token));
        return new ResponseEntity<>("",headers,HttpStatus.OK);
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){
        userService.registerUser(registerDto);
        return new ResponseEntity<>("Rejestracja się powiodła, przesłano maila na "+registerDto.getEmail()+". Konto należy potwierdzić w ciągu 7 dni albo zostanie usunięte ", HttpStatus.OK);
    }
    @GetMapping("validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(jwtService.validateToken(token),HttpStatus.OK);
    }
    @PostMapping("logout")
    public ResponseEntity<String> logout(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }

    @PostMapping("token")
    public ResponseEntity<String> refreshToken(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
    @GetMapping("confirm")
    public ResponseEntity<String> confirmAccount(@RequestParam String token){
        userService.confirmUser(token);
        return new ResponseEntity<>("Konto zostało potwierdzone",HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<String> update(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
    @DeleteMapping
    public ResponseEntity<String> delete(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
}
