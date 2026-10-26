package com.Project.SpringBootPreperation.controller;

import com.Project.SpringBootPreperation.entity.UserEntry;
import com.Project.SpringBootPreperation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
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
    @PostMapping
    public ResponseEntity<UserEntry> saveUser(@RequestBody UserEntry userEntry){
        return ResponseEntity.ok(userService.saveUser(userEntry));
    }
    @GetMapping
    public ResponseEntity<List<UserEntry>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());}

    @PutMapping("/{id}")
    public ResponseEntity<UserEntry> updateUser(
            @PathVariable Long id,
            @RequestBody UserEntry userEntry) {

        return ResponseEntity.ok(userService.userUpdate(id, userEntry));
    }
    @PostMapping("/rollback")
    public ResponseEntity<String> testRollback(
            @RequestBody UserEntry user) throws Exception {

        userService.SaveUserWithRollback(user);

        return ResponseEntity.ok("User saved successfully");
    }
}
