package com.example.loan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/user")
public class UserController {

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello, World!");

        // Return JSON response with HTTP status 200 (OK)
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
