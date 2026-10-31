package com.Project.SpringBootPreperation.controller;

import com.Project.SpringBootPreperation.dto.AuthResponse;
import com.Project.SpringBootPreperation.dto.LoginRequest;
import com.Project.SpringBootPreperation.dto.RefreshRequest;
import com.Project.SpringBootPreperation.dto.RegisterRequest;
import com.Project.SpringBootPreperation.service.AuthService;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {

        try {
            authService.register(request);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "User registered successfully"));

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        try {
            return ResponseEntity.ok(
                    authService.login(request)
            );

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @RequestBody RefreshRequest request) {

        try {
            return ResponseEntity.ok(
                    authService.refresh(request.refreshToken())
            );

        } catch (JwtException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid or expired refresh token"));
        }
    }
}