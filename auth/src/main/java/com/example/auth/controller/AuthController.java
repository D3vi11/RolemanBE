package com.example.auth.controller;

import com.example.auth.dto.RegisterDto;
import com.example.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @GetMapping
    public String test(){
        return "Authorization";
    }

    @PostMapping("login")
    public void login(){

    }
    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){
        userService.registerUser(registerDto);
        return new ResponseEntity<>("Rejestracja się powiodła, przesłano maila na xd@xd.xd. Konto należy potwierdzić w ciągu 7 dni albo zostanie usunięte ", HttpStatus.OK);
    }
    @PutMapping
    public void update(){

    }
    @DeleteMapping
    public void delete(){

    }
}
