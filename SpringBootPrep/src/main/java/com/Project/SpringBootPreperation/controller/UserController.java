package com.Project.SpringBootPreperation.controller;

import com.Project.SpringBootPreperation.entity.UserEntry;
import com.Project.SpringBootPreperation.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired private UserRepo userRepo;

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
    //derived Queries

    @GetMapping("/derived/name/{name}") public List<UserEntry> findAllByName(@PathVariable String name) {
        return userRepo.findAllByName(name); }
    @GetMapping("/derived/email/{email}") public Optional<UserEntry> findByEmail(@PathVariable String email) {
        return userRepo.findByEmail(email); }
    @GetMapping("/derived/exists/{email}") public boolean existsByEmail(@PathVariable String email) {
        return userRepo.existsByEmail(email); }

    //JPQL Queries
     @GetMapping("/jpql/name/{name}") public List<UserEntry> searchByNameJPQL(@PathVariable String name) {
        return userRepo.searchByName(name); }
     @GetMapping("/jpql/email/{email}") public Optional<UserEntry> searchByEmailJPQL(@PathVariable String email) {
        return userRepo.searchByEmail(email); }

    //Native Queries
    @GetMapping("/native/name/{name}") public List<UserEntry> searchByNameNative(@PathVariable String name) {
        return userRepo.findByNameNative(name); }
    @GetMapping("/native/email/{email}") public Optional<UserEntry> searchByEmailNative(@PathVariable String email) {
        return userRepo.findByEmailNative(email); }
}
