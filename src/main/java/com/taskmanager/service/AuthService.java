package com.taskmanager.service;

import com.taskmanager.dto.*;
import com.taskmanager.exception.DuplicateEmailException;
import com.taskmanager.exception.UserNotFoundException;
import com.taskmanager.model.*;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new DuplicateEmailException(request.getEmail());
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(UserRole.USER);

        User saved = userRepository.save(user);
        String token = jwtService.generateToken(saved.getEmail(), saved.getId());

        return new AuthResponse(token, saved.getId(), saved.getEmail(), saved.getName());
    }

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        String token = jwtService.generateToken(user.getEmail(), user.getId());

        return new AuthResponse(token, user.getId(), user.getEmail(), user.getName());
    }

}
