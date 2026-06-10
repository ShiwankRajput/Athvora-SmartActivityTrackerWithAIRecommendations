package com.shivnexEngineering.FitnessTrackerApplication.service;

import com.shivnexEngineering.FitnessTrackerApplication.dto.LoginRequest;
import com.shivnexEngineering.FitnessTrackerApplication.dto.LoginResponse;
import com.shivnexEngineering.FitnessTrackerApplication.dto.RegisterRequest;
import com.shivnexEngineering.FitnessTrackerApplication.dto.UserResponse;
import com.shivnexEngineering.FitnessTrackerApplication.entity.User;
import com.shivnexEngineering.FitnessTrackerApplication.mapper.UserMapper;
import com.shivnexEngineering.FitnessTrackerApplication.repository.UserRepository;
import com.shivnexEngineering.FitnessTrackerApplication.security.CustomUserDetails;
import com.shivnexEngineering.FitnessTrackerApplication.utils.JwtUtils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, 
        AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(RegisterRequest registerRequest){
        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .build();

        User savedUser = userRepository.save(user);
        UserResponse userResponse = userMapper.mapToUserResponse(savedUser);

        return userResponse;
    }

    public LoginResponse loginUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );

        if(authentication.isAuthenticated()){
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtUtils.generateToken(customUserDetails.getId(), customUserDetails.getRole().name());
            return new LoginResponse(token);
        }

        throw new RuntimeException("Invalid email or password");
    }

}
