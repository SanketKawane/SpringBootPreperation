package com.Project.SpringBootPreperation.service;

import com.Project.SpringBootPreperation.entity.UserEntry;
import com.Project.SpringBootPreperation.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private  UserRepo userRepo ;
    @Cacheable(value = "users", key = "#id")
    public UserEntry getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @CachePut(value = "users", key = "#result.id")
    public UserEntry updateUser(UserEntry user) {
        return userRepo.save(user);
    }
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

}
