package com.Project.SpringBootPreperation.controller;

import com.Project.SpringBootPreperation.entity.UserEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private Map<Long,UserEntry> userEntries=new HashMap<>();
    @GetMapping
    public List<UserEntry> getAll(){
        return new ArrayList<>(userEntries.values());
    }
    @PostMapping
    public boolean putData(@RequestBody UserEntry userEntry){
        userEntries.put(userEntry.getId(),userEntry);
        return true;
    }
    @GetMapping("id/{Id}")
    public UserEntry getById(@PathVariable Long Id){
        return userEntries.get(Id);
    }
}
