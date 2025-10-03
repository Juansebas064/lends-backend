package com.app.lends_backend.controller;

import com.app.lends_backend.dto.UserDto;
import com.app.lends_backend.model.User;
import com.app.lends_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<UserDto>> getAllUsers() {
        var response = userService.getAllUsers();
        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody User user) {
        var response = userService.registerUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    boolean isMatch = passwordEncoder.matches(rawPassword, encodedPassword);

}
