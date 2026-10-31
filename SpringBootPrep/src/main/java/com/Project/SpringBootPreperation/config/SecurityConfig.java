package com.Project.SpringBootPreperation.config;


import com.Project.SpringBootPreperation.repository.UserDetailsRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(
            UserDetailsRepo repository) {

        return email -> repository.findByEmail(email)
                .map(appUser -> User.withUsername(appUser.getEmail())
                        .password(appUser.getPassword())
                        .roles(appUser.getRole())
                        .build())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            com.Project.SpringBootPreperation.security.JwtAuthenticationFilter jwtFilter,
            DaoAuthenticationProvider authenticationProvider)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        ))

                .authenticationProvider(authenticationProvider)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,
                                "/auth/register",
                                "/auth/login",
                                "/auth/refresh")
                        .permitAll()

                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/users/**")
                        .hasAnyRole("USER", "ADMIN")

                        .anyRequest()
                        .authenticated()
                )

                .addFilterBefore(
                        jwtFilter,
                        org.springframework.security.web.authentication
                                .UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}