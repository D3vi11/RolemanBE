package com.example.auth;

import com.example.auth.dto.*;
import com.example.auth.entity.User;
import com.example.auth.exception.IncorrectConfirmationException;
import com.example.auth.exception.IncorrectLoginException;
import com.example.auth.exception.IncorrectRegistrationException;
import com.example.auth.exception.UserExistsException;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.EmailService;
import com.example.auth.service.JwtService;
import com.example.auth.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;
    private static final String username = "username";
    private static final String password = "password";
    private static final String email = "email@email.com";

    @Nested
    public class RegisterUserTests {

        private static final RegisterDto registerDto = mock(RegisterDto.class);
        private static final User user = new User(username, password, email, true, 1, "", Instant.now());

        @BeforeEach
        public void prepareDto() {
            when(registerDto.getEmail()).thenReturn(email);
            when(registerDto.getUsername()).thenReturn(username);
            when(registerDto.getPassword()).thenReturn(password);
        }

        @Test
        public void registerUser() {
            when(userRepository.findByUsername(registerDto.getUsername())).thenReturn(Optional.empty());
            when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(Optional.empty());
            when(passwordEncoder.encode(registerDto.getPassword())).thenReturn(password);
            userService.registerUser(registerDto);
            ArgumentCaptor<String> tokenCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(emailService).sendEmail(eq("email@email.com"), tokenCaptor.capture());
            verify(userRepository).save(userCaptor.capture());
            assertEquals(userCaptor.getValue().getUsername(), username);
            assertEquals(userCaptor.getValue().getPassword(), password);
            assertEquals(userCaptor.getValue().getEmail(), email);
        }

        @Test
        public void registerUserNameExists() {
            when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User(username, password, "abc@gmail.com", true, 7, "abc", Instant.now())));
            assertThrows(IncorrectRegistrationException.class, () -> userService.registerUser(registerDto));
            verify(emailService, never()).sendEmail("", "");
            verify(userRepository, never()).save(user);
        }

        @Test
        public void registerUserEmailExists() {
            when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User("abc", password, email, true, 7, "abc", Instant.now())));
            assertThrows(IncorrectRegistrationException.class, () -> userService.registerUser(registerDto));
            verify(emailService, never()).sendEmail("", "");
            verify(userRepository, never()).save(user);
        }
    }

    @Nested
    public class ConfirmUserTests {

        private static final User user = new User(username, password, email, true, 1, "", Instant.now());

        @Test
        public void confirmUserTest() {
            String token = "abc";
            user.setIsActive(false);
            when(userRepository.findByConfirmationToken(token)).thenReturn(Optional.of(user));
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            userService.confirmUser(token);
            verify(userRepository).save(userCaptor.capture());
            assertTrue(userCaptor.getValue().getIsActive());
        }

        @Test
        public void incorrectTokenTest() {
            String token = "abc";
            user.setIsActive(false);
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            assertThrows(IncorrectConfirmationException.class, () -> userService.confirmUser(token + "abc"));
            verify(userRepository, never()).save(userCaptor.capture());
        }

        @Test
        public void accountAlreadyActiveTest() {
            String token = "abc";
            when(userRepository.findByConfirmationToken(token)).thenReturn(Optional.of(user));
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            assertThrows(IncorrectConfirmationException.class, () -> userService.confirmUser(token));
            verify(userRepository, never()).save(userCaptor.capture());
        }
    }

    @Nested
    public class LoginUser {

        private static final User user = new User(username, password, email, true, 1, "", Instant.now());
        private static LoginDto loginDto = mock(LoginDto.class);

        @BeforeEach
        public void prepareDto() {
            when(loginDto.getUsername()).thenReturn("username");
            when(loginDto.getPassword()).thenReturn("password");
        }

        @Test
        public void loginTest() {
            when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);
            userService.loginUser(loginDto);
            ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
            verify(jwtService).generateToken(usernameCaptor.capture());
            assertEquals(usernameCaptor.getValue(), username);
        }

        @Test
        public void incorrectLoginTest() {
            when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.empty());
            assertThrows(IncorrectLoginException.class, () -> userService.loginUser(loginDto));
            ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
            verify(jwtService, never()).generateToken(usernameCaptor.capture());
        }

        @Test
        public void incorrectPasswordTest() {
            when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(false);
            assertThrows(IncorrectLoginException.class, () -> userService.loginUser(loginDto));
            ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
            verify(jwtService, never()).generateToken(usernameCaptor.capture());
        }

        @Test
        public void userActiveTest() {
            when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(false);
            user.setIsActive(true);
            assertThrows(IncorrectLoginException.class, () -> userService.loginUser(loginDto));
            ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
            verify(jwtService, never()).generateToken(usernameCaptor.capture());
        }

    }

    @Nested
    public class ChangePassword {
        private static final User user = new User(username, password, email, true, 1, "", Instant.now());
        private static ChangePasswordDto changePasswordDto = new ChangePasswordDto(username, password, password);

        @Test
        public void changePasswordTest() {
            when(userRepository.findByUsername(changePasswordDto.getUsername())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(changePasswordDto.getPassword(), user.getPassword())).thenReturn(true);
            when(passwordEncoder.encode(changePasswordDto.getNewPassword())).thenReturn("EncodedPassword");
            userService.changePassword(changePasswordDto);
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).save(userCaptor.capture());
            assertEquals("EncodedPassword", userCaptor.getValue().getPassword());
        }

        @Test
        public void userNotFoundTest(){
            when(userRepository.findByUsername(changePasswordDto.getUsername())).thenReturn(Optional.empty());
            assertThrows(IncorrectLoginException.class, () -> userService.changePassword(changePasswordDto));
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, never()).save(userCaptor.capture());
        }

        @Test
        public void incorrectPasswordTest(){
            when(userRepository.findByUsername(changePasswordDto.getUsername())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(changePasswordDto.getPassword(), user.getPassword())).thenReturn(false);
            assertThrows(IncorrectLoginException.class, () -> userService.changePassword(changePasswordDto));
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, never()).save(userCaptor.capture());
        }
    }

    @Nested
    public class ChangeUsername {
        private static final User user = new User(username, password, email, true, 1, "", Instant.now());
        private static final String newUsername = "newUsername";
        private static final ChangeUsernameDto changeUsernameDto = new ChangeUsernameDto(username, password, newUsername);

        @Test
        public void changeUsernameTest(){
            when(userRepository.findByUsername(changeUsernameDto.getUsername())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(changeUsernameDto.getPassword(), user.getPassword())).thenReturn(true);
            userService.changeUsername(changeUsernameDto);
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).save(userCaptor.capture());
            assertEquals(newUsername, userCaptor.getValue().getUsername());
        }

        @Test
        public void userNotFoundTest(){
            when(userRepository.findByUsername(changeUsernameDto.getUsername())).thenReturn(Optional.empty());
            assertThrows(IncorrectLoginException.class, () -> userService.changeUsername(changeUsernameDto));
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, never()).save(userCaptor.capture());
        }

        @Test
        public void incorrectPasswordTest(){
            when(userRepository.findByUsername(changeUsernameDto.getUsername())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(changeUsernameDto.getPassword(), user.getPassword())).thenReturn(false);
            assertThrows(IncorrectLoginException.class, () -> userService.changeUsername(changeUsernameDto));
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, never()).save(userCaptor.capture());
        }

        @Test
        public void usernameExistsTest(){
            when(userRepository.findByUsername(changeUsernameDto.getUsername())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(changeUsernameDto.getPassword(), user.getPassword())).thenReturn(true);
            when(userRepository.findByUsername(changeUsernameDto.getNewUsername())).thenReturn(Optional.of(new User()));
            assertThrows(UserExistsException.class, () -> userService.changeUsername(changeUsernameDto));
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, never()).save(userCaptor.capture());
        }
    }

    @Nested
    public class ChangeEmail {
        private static final User user = new User(username, password, email, true, 1, "", Instant.now());
        private static final String newEmail = "newEmail@email.com";
        private static final ChangeEmailDto changeEmailDto = new ChangeEmailDto(username, password, newEmail);

        @Test
        public void changeEmailTest(){
            when(userRepository.findByUsername(changeEmailDto.getUsername())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(changeEmailDto.getPassword(), user.getPassword())).thenReturn(true);
            userService.changeEmail(changeEmailDto);
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).save(userCaptor.capture());
            assertEquals(newEmail, userCaptor.getValue().getEmail());
        }

        @Test
        public void userNotFoundTest(){
            when(userRepository.findByUsername(changeEmailDto.getUsername())).thenReturn(Optional.empty());
            assertThrows(IncorrectLoginException.class, () -> userService.changeEmail(changeEmailDto));
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, never()).save(userCaptor.capture());
        }

        @Test
        public void incorrectPasswordTest(){
            when(userRepository.findByUsername(changeEmailDto.getUsername())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(changeEmailDto.getPassword(), user.getPassword())).thenReturn(false);
            assertThrows(IncorrectLoginException.class, () -> userService.changeEmail(changeEmailDto));
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, never()).save(userCaptor.capture());
        }

        @Test
        public void emailExistsTest(){
            when(userRepository.findByUsername(changeEmailDto.getUsername())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(changeEmailDto.getPassword(), user.getPassword())).thenReturn(true);
            when(userRepository.findByEmail(changeEmailDto.getNewEmail())).thenReturn(Optional.of(new User()));
            assertThrows(UserExistsException.class, () -> userService.changeEmail(changeEmailDto));
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, never()).save(userCaptor.capture());
        }
    }

    @Nested
    public class DeleteUser {
        private static final User user = new User(username, password, email, true, 1, "", Instant.now());
        private static final LoginDto loginDto = new LoginDto(username, password);

        @Test
        public void deleteUserTest(){
            when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);
            userService.deleteUser(loginDto);
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).delete(userCaptor.capture());
            assertEquals(loginDto.getUsername(), userCaptor.getValue().getUsername());
            assertEquals(loginDto.getPassword(), userCaptor.getValue().getPassword());
        }

        @Test
        public void userNotFoundTest(){
            when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.empty());
            assertThrows(IncorrectLoginException.class, () -> userService.deleteUser(loginDto));
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, never()).delete(userCaptor.capture());
        }

        @Test
        public void incorrectPasswordTest(){
            when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(false);
            assertThrows(IncorrectLoginException.class, () ->userService.deleteUser(loginDto));
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, never()).delete(userCaptor.capture());
        }
    }
}
