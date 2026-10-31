package com.Project.SpringBootPreperation.service;

import com.Project.SpringBootPreperation.dto.AuthResponse;
import com.Project.SpringBootPreperation.dto.LoginRequest;
import com.Project.SpringBootPreperation.dto.RegisterRequest;
import com.Project.SpringBootPreperation.entity.UserDetails;
import com.Project.SpringBootPreperation.repository.UserDetailsRepo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {



        private final UserDetailsRepo repository;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;
        private final JWTService jwtService;

        public AuthService(
                UserDetailsRepo repository,
                PasswordEncoder passwordEncoder,
                AuthenticationManager authenticationManager,
                JWTService jwtService
        ) {
            this.repository = repository;
            this.passwordEncoder = passwordEncoder;
            this.authenticationManager = authenticationManager;
            this.jwtService = jwtService;
        }

        public void register(RegisterRequest request) {

            if (repository.findByEmail(request.email()).isPresent()) {
                throw new IllegalArgumentException("Email already registered");
            }

            UserDetails user = new UserDetails(
                    request.email(),
                    passwordEncoder.encode(request.password()),
                    "USER"
            );

            repository.save(user);
        }

        public AuthResponse login(LoginRequest request) {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.email(),
                                    request.password()
                            )
                    );

            UserDetails user = repository.findByEmail(
                    authentication.getName()
            ).orElseThrow();

            return new AuthResponse(
                    jwtService.generateAccessToken(user),
                    jwtService.generateRefreshToken(user),
                    "Bearer"
            );
        }

        public AuthResponse refresh(String refreshToken) {

            Claims claims = jwtService.extractClaim(refreshToken);

            if (!"refresh".equals(
                    claims.get("tokenType", String.class))) {
                throw new IllegalArgumentException("Invalid refresh token");
            }

            UserDetails user = repository.findByEmail(
                    claims.getSubject()
            ).orElseThrow();

            if (claims.getExpiration() == null
                    || !claims.getExpiration().after(new java.util.Date())) {
                throw new IllegalArgumentException("Refresh token expired");
            }

            return new AuthResponse(
                    jwtService.generateAccessToken(user),
                    jwtService.generateRefreshToken(user),
                    "Bearer"
            );
        }
    }
