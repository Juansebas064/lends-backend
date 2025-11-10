package com.app.lends_backend.controller;

import com.app.lends_backend.dto.UserDto;
import com.app.lends_backend.model.User;
import com.app.lends_backend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping(path = "/setup-admin-user")
    public ResponseEntity<UserDto> registerFirstAdminUser(@RequestBody User user) {
        try {
            var response = authService.registerFirstAdminUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    boolean isMatch = passwordEncoder.matches(rawPassword, encodedPassword);

}
