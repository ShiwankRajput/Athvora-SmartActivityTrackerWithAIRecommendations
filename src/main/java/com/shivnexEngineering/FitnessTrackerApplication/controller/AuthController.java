package com.shivnexEngineering.FitnessTrackerApplication.controller;

import com.shivnexEngineering.FitnessTrackerApplication.dto.LoginRequest;
import com.shivnexEngineering.FitnessTrackerApplication.dto.LoginResponse;
import com.shivnexEngineering.FitnessTrackerApplication.dto.RegisterRequest;
import com.shivnexEngineering.FitnessTrackerApplication.dto.UserResponse;
import com.shivnexEngineering.FitnessTrackerApplication.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    // Register a new User
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    // Login a User
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

}
