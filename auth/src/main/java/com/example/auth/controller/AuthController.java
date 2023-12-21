package com.example.auth.controller;

import com.example.auth.dto.*;
import com.example.auth.service.JwtService;
import com.example.auth.service.UserService;
import com.example.auth.validation.annotation.Email;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        return new ResponseEntity<>(jsonObject.toString(),headers,HttpStatus.OK);
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){
        userService.registerUser(registerDto);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        jsonObject.put("message","Rejestracja się powiodła, przesłano maila na "+registerDto.getEmail()+". Konto należy potwierdzić w ciągu 7 dni albo zostanie usunięte ");
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }
    @GetMapping("validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        jsonObject.put("valid",jwtService.validateToken(token));
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @PostMapping("token")
    public ResponseEntity<String> refreshToken(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.I_AM_A_TEAPOT);
        jsonObject.put("message","Work in progress");
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.I_AM_A_TEAPOT);
    }
    @GetMapping("confirm")
    public ResponseEntity<String> confirmAccount(@RequestParam String token){
        userService.confirmUser(token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        jsonObject.put("message","Konto zostało potwierdzone");
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }
    @PutMapping("change/password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto){
        userService.changePassword(changePasswordDto);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        jsonObject.put("message","Hasło zostało zmienione");
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }
    @PutMapping("change/username")
    public ResponseEntity<String> changeUsername(@Valid @RequestBody ChangeUsernameDto changeUsernameDto){
        userService.changeUsername(changeUsernameDto);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        jsonObject.put("message","Nazwa użytkownika została zmieniona na: "+changeUsernameDto.getNewUsername());
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }
    @PutMapping("change/email")
    public ResponseEntity<String> changeEmail(@Valid @RequestBody ChangeEmailDto changeEmailDto){
        userService.changeEmail(changeEmailDto);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        jsonObject.put("message","Email został zmieniony na: "+ changeEmailDto.getNewEmail());
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }
    @DeleteMapping("user")
    public ResponseEntity<String> delete(@Valid @RequestBody LoginDto loginDto){
        userService.deleteUser(loginDto);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.OK);
        jsonObject.put("message","Użytkownik: "+loginDto.getUsername()+" został usunięty");
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }
}
