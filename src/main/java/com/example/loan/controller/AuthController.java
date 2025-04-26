package com.example.loan.controller;

import com.example.loan.config.SecurityConfig;
import com.example.loan.dto.LoginRequest;
import com.example.loan.dto.RegisterRequest;
import com.example.loan.entity.UserEntity;
import com.example.loan.exception.CustomException;
import com.example.loan.model.BaseResponse;
import com.example.loan.service.UserService;
import com.example.loan.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
//import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserEntity>> register(@RequestBody @Valid RegisterRequest registerRequest) {
        try {
            if (userService.findByUsername(registerRequest.getUsername()).isPresent()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Username Already Exists");
            }

            String hashedPassword = securityConfig.passwordEncoder().encode(registerRequest.getPassword());
            UserEntity newUser = new UserEntity();
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(hashedPassword);
            newUser.setRole(registerRequest.getRole());

            UserEntity savedUser = userService.createUser(newUser);;

            BaseResponse<UserEntity> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "User Registered Successfully", null);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<Map<String, String>>> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            Optional<UserEntity> user = userService.findByUsername(loginRequest.getUsername());
            if (user.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Username or Password");
            }

            if (!securityConfig.passwordEncoder().matches(loginRequest.getPassword(), user.get().getPassword())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Username or Password");
            }

            String token = jwtUtils.generateToken(user.get().getUsername(), user.get().getId());
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("accessToken", token);


            BaseResponse<Map<String, String>> successResponse = new BaseResponse<>(HttpStatus.OK.value(), "User Login Successfully", responseBody);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            if (e instanceof CustomException customException) {
                throw customException; // rethrow with its own status and message
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }
}
