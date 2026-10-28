package com.Project.SpringBootPreperation.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class SecurityController {
    @GetMapping("/hello")
    public String hello() {
        return "Welcome to the User API";
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_READ')")
    public String getUsers() {
        return "You can view user information";
    }
    @PostMapping
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public String createUser() {
        return "User created successfully";
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public String updateUser(@PathVariable long id){
        return "user updated successfully :"+id;
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint() {
        return "Welcome, Admin!";
    }


}
