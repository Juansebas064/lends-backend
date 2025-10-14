package com.app.lends_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<String> home(@RequestParam(defaultValue = "Dev") String user) {
        return ResponseEntity.ok(String.format("Lends backend up and running. Hello, %s", user));
    }
}
