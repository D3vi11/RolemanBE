package com.example.auth.controller;

import com.example.auth.dto.*;
import com.example.auth.service.JwtService;
import com.example.auth.service.UserService;
import com.example.auth.validation.annotation.Email;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("token")
    public ResponseEntity<String> refreshToken(){
        return new ResponseEntity<>("Work in progress",HttpStatus.I_AM_A_TEAPOT);
    }
    @GetMapping("confirm")
    public ResponseEntity<String> confirmAccount(@RequestParam String token){
        userService.confirmUser(token);
        return new ResponseEntity<>("Konto zostało potwierdzone",HttpStatus.OK);
    }
    @PutMapping("change/password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto){
        userService.changePassword(changePasswordDto);
        return new ResponseEntity<>("Hasło zostało zmienione",HttpStatus.OK);
    }
    @PutMapping("change/username")
    public ResponseEntity<String> changeUsername(@Valid @RequestBody ChangeUsernameDto changeUsernameDto){
        userService.changeUsername(changeUsernameDto);
        return new ResponseEntity<>("Nazwa użytkownika została zmieniona na: "+changeUsernameDto.getNewUsername(),HttpStatus.OK);
    }
    @PutMapping("change/email")
    public ResponseEntity<String> changeEmail(@Valid @RequestBody ChangeEmailDto changeEmailDto){
        userService.changeEmail(changeEmailDto);
        return new ResponseEntity<>("Email został zmieniony na: "+ changeEmailDto.getNewEmail(),HttpStatus.OK);
    }
    @DeleteMapping("user")
    public ResponseEntity<String> delete(@Valid @RequestBody LoginDto loginDto){
        userService.deleteUser(loginDto);
        return new ResponseEntity<>("Użytkownik: "+loginDto.getUsername()+" został usunięty", HttpStatus.OK);
    }
}
