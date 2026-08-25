package com.Project.SpringBootPreperation.repository;

import com.Project.SpringBootPreperation.entity.UserEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntry,Long> {
}
