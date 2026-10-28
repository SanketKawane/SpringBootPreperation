package com.Project.SpringBootPreperation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {


        @GetMapping("/public/hello")
        public String publicHello() {
            return "This is a public endpoint";
        }

        @GetMapping("/user/profile")
        public String userProfile() {
            return "Welcome to the user profile";
        }

        @GetMapping("/admin/dashboard")
        public String adminDashboard() {
            return "Welcome to the admin dashboard";
        }
}
