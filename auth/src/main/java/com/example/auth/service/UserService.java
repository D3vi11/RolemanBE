package com.example.auth.service;

import com.example.auth.dto.LoginDto;
import com.example.auth.dto.RegisterDto;
import com.example.auth.entity.User;
import com.example.auth.exception.IncorrectConfirmationException;
import com.example.auth.exception.IncorrectLoginException;
import com.example.auth.exception.IncorrectRegistrationException;
import com.example.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    EmailService emailService;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;

    public void registerUser(RegisterDto registerDto) {
        if(!userRepository.findByUsername(registerDto.getUsername()).isEmpty()){
            throw new IncorrectRegistrationException("Użytkownik z tym loginem istnieje");
        }
        if(!userRepository.findByEmail(registerDto.getEmail()).isEmpty()){
            throw new IncorrectRegistrationException("Użytkownik z tym emailem istnieje");
        }
        String token = generateToken();
        emailService.sendEmail(registerDto.getEmail(),token);
        userRepository.save(mapToUser(registerDto,token));
    }

    public void confirmUser(String token){
        List<User> users = userRepository.findByConfirmationToken(token);
        if(users.isEmpty()){
            throw new IncorrectConfirmationException("Token weryfikacyjny niepoprawny lub nieważny");
        }
        if(users.get(0).getIsActive()){
            throw new IncorrectConfirmationException("Konto zostało już potwierdzone");
        }
        users.get(0).setIsActive(true);
        userRepository.save(users.get(0));
    }

    public String loginUser(LoginDto loginDto){
        List<User> users = userRepository.findByUsername(loginDto.getUsername());
        if(users.isEmpty()||!passwordEncoder.matches(loginDto.getPassword(),users.get(0).getPassword())){
            throw new IncorrectLoginException("Niepoprawny login lub hasło");
        }
        if(!users.get(0).getIsActive()){
            throw new IncorrectLoginException("Użytkownik nie jest aktywny");
        }
        return jwtService.generateToken(loginDto.getUsername());
    }

    private User mapToUser(RegisterDto registerDto, String token){
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        return new User(registerDto.getUsername(), encodedPassword, registerDto.getEmail(), false, 7, token, Instant.now());
    }
    private String generateToken(){
        return UUID.randomUUID().toString();
    }
}
