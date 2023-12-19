package com.example.auth.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    @NonNull
    private JavaMailSender javaMailSender;
    @Value("${app.address}")
    private String appAddress;

    public void sendEmail(String email, String token){
        String confirmationUrl = appAddress+"/confirm?token="+token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("Roleman");
        message.setSubject("Roleman potwierdzenie adresu email");
        message.setText("W celu potwierdzenia konta proszę kliknąć link: "+confirmationUrl);

        javaMailSender.send(message);
    }
}
