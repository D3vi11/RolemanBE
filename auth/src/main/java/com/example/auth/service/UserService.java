package com.example.auth.service;

import com.example.auth.dto.RegisterDto;
import com.example.auth.entity.User;
import com.example.auth.exception.IncorrectRegistrationException;
import com.example.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    EmailService emailService;

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

    private User mapToUser(RegisterDto registerDto, String token){
        User user = new User(registerDto.getUsername(), registerDto.getPassword(), registerDto.getEmail(), false, 7, token, Instant.now());
        return user;
    }
    private String generateToken(){
        return UUID.randomUUID().toString();
    }
}
