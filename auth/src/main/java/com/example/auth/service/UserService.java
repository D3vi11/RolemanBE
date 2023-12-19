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
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    EmailService emailService;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;

    public void registerUser(RegisterDto registerDto) {
        userRepository.findByUsername(registerDto.getUsername())
                .ifPresent(u -> {
                    throw new IncorrectRegistrationException("Użytkownik z tym loginem istnieje");
                });

        userRepository.findByUsername(registerDto.getEmail())
                .ifPresent(u -> {
                    throw new IncorrectRegistrationException("Użytkownik z tym emailem istnieje");
                });

        String token = generateToken();
        emailService.sendEmail(registerDto.getEmail(), token);
        userRepository.save(mapToUser(registerDto, token));
    }

    public void confirmUser(String token) {
        User user = userRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new IncorrectConfirmationException("Token weryfikacyjny niepoprawny lub nieważny"));

        if (user.getIsActive()) {
            throw new IncorrectConfirmationException("Konto zostało już potwierdzone");
        }
        user.setIsActive(true);
        userRepository.save(user);
    }

    public String loginUser(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername())
                .filter(u -> passwordEncoder.matches(loginDto.getPassword(), u.getPassword()))
                .orElseThrow(() -> new IncorrectLoginException("Niepoprawny login lub hasło"));

        if (!user.getIsActive()) {
            throw new IncorrectLoginException("Użytkownik nie jest aktywny");
        }
        return jwtService.generateToken(loginDto.getUsername());
    }

    public void changePassword(LoginDto loginDto, String newPassword) {

    }

    private User mapToUser(RegisterDto registerDto, String token) {
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        return new User(registerDto.getUsername(), encodedPassword, registerDto.getEmail(), false, 7, token, Instant.now());
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
