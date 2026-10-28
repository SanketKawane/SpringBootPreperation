package com.Project.SpringBootPreperation.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;






    @Configuration
    @EnableMethodSecurity(securedEnabled = true)
    public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                throws Exception {

            http
                    .csrf(csrf -> csrf.disable())

                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/public/**").permitAll()

                            .requestMatchers("/admin/**")
                            .hasRole("ADMIN")

                            .requestMatchers("/manager/**")
                            .hasRole("MANAGER")

                            .requestMatchers("/api/users")
                            .hasAuthority("USER_READ")

                            .requestMatchers("/api/users/**")
                            .hasAnyAuthority(
                                    "USER_READ",
                                    "USER_CREATE",
                                    "USER_UPDATE",
                                    "USER_DELETE"
                            )

                            .requestMatchers("/user/**")
                            .authenticated()

                            .anyRequest().authenticated()
                    )

                    .httpBasic(Customizer.withDefaults());

            return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService(
                PasswordEncoder passwordEncoder) {

            UserDetails normalUser = User.builder()
                    .username("sanket")
                    .password(passwordEncoder.encode("user123"))
                    .authorities("USER_READ",
                            "USER_CREATE",
                            "USER_UPDATE")
                    .build();

            UserDetails admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .roles("ADMIN")
                    .authorities(
                            "USER_READ",
                            "USER_CREATE",
                            "USER_UPDATE",
                            "USER_DELETE"
                    )
                    .build();

            UserDetails manager = User.builder()
                    .username("manager")
                    .password(passwordEncoder.encode("manager123"))
                    .roles("MANAGER")
                    .authorities("USER_READ")
                    .build();

            return new InMemoryUserDetailsManager(
                    normalUser,
                    admin,
                    manager
            );
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
}
