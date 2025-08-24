package com.example.auth;

import com.example.auth.service.EmailService;
import com.example.auth.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTests {

    private static final String email = "email@email.com";
    private static final String token = "abcdefgh";
    private static final String appAddress = "testAddress.com";

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    EmailService emailService;

    @BeforeEach
    public void setAddress(){
        ReflectionTestUtils.setField(emailService, "appAddress", appAddress);
    }

    @Test
    public void sendEmailTest(){
        emailService.sendEmail(email, token);
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender).send(messageCaptor.capture());
        assertEquals(email, messageCaptor.getValue().getTo()[0]);
        assertEquals("Roleman",messageCaptor.getValue().getFrom());
        assertEquals("Roleman potwierdzenie adresu email",messageCaptor.getValue().getSubject());
        assertEquals("W celu potwierdzenia konta proszę kliknąć link: "+ appAddress +"/authorization/confirm?token="+token,messageCaptor.getValue().getText());
    }
}
