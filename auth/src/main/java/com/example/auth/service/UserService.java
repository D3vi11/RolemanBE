package com.example.auth.service;

import com.example.auth.dto.RegisterDto;
import com.example.auth.entity.User;
import com.example.auth.exception.IncorrectRegistrationException;
import com.example.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    public void registerUser(RegisterDto registerDto) {
        if(!userRepository.findByUsername(registerDto.getUsername()).isEmpty()){
            throw new IncorrectRegistrationException("Użytkownik z tym loginem istnieje");
        }
        if(!userRepository.findByEmail(registerDto.getEmail()).isEmpty()){
            throw new IncorrectRegistrationException("Użytkownik z tym emailem istnieje");
        }
        userRepository.save(mapToUser(registerDto));
    }

    private User mapToUser(RegisterDto registerDto){
        User user = new User(registerDto.getUsername(), registerDto.getPassword(), registerDto.getEmail(), false, 7, "testToken", Instant.now());
        return user;
    }
}
