package com.app.lends_backend.service;

import com.app.lends_backend.dto.UserDto;
import com.app.lends_backend.mapper.UserMapper;
import com.app.lends_backend.model.User;
import com.app.lends_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto registerFirstAdminUser(User user) {
        if (userRepository.count() == 0) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userMapper.toDto(userRepository.save(user));
        }
        return null;
    }
}
