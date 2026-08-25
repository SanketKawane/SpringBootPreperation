package com.Project.SpringBootPreperation.service;

import com.Project.SpringBootPreperation.entity.UserEntry;
import com.Project.SpringBootPreperation.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private  UserRepo userRepo ;


}
