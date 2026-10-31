package com.Project.SpringBootPreperation.repository;

import com.Project.SpringBootPreperation.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepo extends JpaRepository<UserDetails,Long> {

    Optional<UserDetails> findByEmail(String email);

}
