package com.app.lends_backend.service;

import com.app.lends_backend.dto.UserDto;
import com.app.lends_backend.mapper.UserMapper;
import com.app.lends_backend.model.User;
import com.app.lends_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void whenRegisterUser_thenPasswordShouldBeEncodedAndUserSaved() {
        // First step: Arrange the data
        User userToRegister = new User(
                null,
                "John",
                "Doe",
                "IT Leader",
                "3001112233",
                "admin@example.com",
                "AdminPassword_123",
                "admin"
        );
        User savedUser = new User(
                1,
                "John",
                "Doe",
                "IT Leader",
                "3001112233",
                "admin@example.com",
                "EncodedAdminPassword_456",
                "admin"
        );
        UserDto expectedUserDto = new UserDto(
                1,
                "John",
                "Doe",
                "IT Leader",
                "3001112233",
                "admin@example.com",
                "admin"
        );

        // Second step: Define mocks behavior
        when(passwordEncoder.encode(userToRegister.getPassword())).thenReturn("EncodedAdminPassword_456");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(expectedUserDto);

        // Third step: Act
        UserDto actualUserDto = userService.registerUser(userToRegister);

        // Fourth step: Assert
        // 1. ¿Is the result the expected one from the mapper?
        assertThat(actualUserDto).isEqualTo(expectedUserDto);

        // 2. ¿Was the original object modified to encode the password?
        assertThat(userToRegister.getPassword()).isEqualTo("EncodedAdminPassword_456");

        // 3. ¿Were the correct methods of our dependencies called? (Verify the "contract")
        verify(passwordEncoder).encode("AdminPassword_123");
        verify(userRepository).save(userToRegister);
        verify(userMapper).toDto(savedUser);
    }
}
