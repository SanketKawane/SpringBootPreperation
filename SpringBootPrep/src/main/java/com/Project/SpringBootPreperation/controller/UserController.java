package com.Project.SpringBootPreperation.controller;

import com.Project.SpringBootPreperation.entity.UserEntry;
import com.Project.SpringBootPreperation.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public List<UserEntry> getAll(){
        return userRepo.findAll();
    }
    @PostMapping
    public UserEntry putData(@RequestBody UserEntry userEntry){
     return userRepo.save(userEntry);

    }
    @GetMapping("id/{Id}")
    public UserEntry getById(@PathVariable Long Id){
        return userRepo.findById(Id).orElse(null);
    }
    @PutMapping("id/{Id}")
    public UserEntry updateUser(@PathVariable Long Id,@RequestBody UserEntry userEntry){
        UserEntry existUser=userRepo.findById(Id).orElse(null);
        if (existUser==null){
            return null;
        }
        existUser.setName(userEntry.getName());
        existUser.setEmail(userEntry.getName());
        return userRepo.save(existUser);
    }
    @DeleteMapping("id/{Id}")
    public String deleteById(@PathVariable Long Id){
        if(!userRepo.existsById(Id)){
            return "User Not Found";
        }
        userRepo.deleteById(Id);
        return "User Deleted";
    }
}
