package com.Project.SpringBootPreperation.service;

import com.Project.SpringBootPreperation.entity.UserEntry;
import com.Project.SpringBootPreperation.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private  UserRepo userRepo ;
    @Transactional
    public UserEntry saveUser(UserEntry userEntry){
        return userRepo.save(userEntry);
    }
    @Transactional(readOnly = true)
    public List<UserEntry>getAllUsers(){
        return userRepo.findAll();
    }
    //transaction with propagation and isolations

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED)
    public UserEntry userUpdate(Long id,UserEntry updatedUser){
        UserEntry userEntry=userRepo.findById(id).orElseThrow(()->
                new RuntimeException("user not found"));
        userEntry.setName(updatedUser.getName());
        userEntry.setEmail(updatedUser.getEmail());
        return userRepo.save(userEntry);
    }
    @Transactional(rollbackFor = Exception.class)
    public UserEntry SaveUserWithRollback(UserEntry userEntry)throws Exception{
        UserEntry savedUser=userRepo.save(userEntry);

        throw new Exception("transacton rollback");
    }

}
