package com.Project.SpringBootPreperation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class healthCheck {
   @GetMapping("/check")
    public String check(){
        return "OK";
    }
}
