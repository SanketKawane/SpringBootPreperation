package com.Project.SpringBootPreperation.repository;

import com.Project.SpringBootPreperation.entity.UserEntry;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntry,Long> {
    //derived Query Methods

    List<UserEntry> findAllByName(String name);

    Optional<UserEntry> findByEmail(String email);

    boolean existsByEmail(String email);

    //JPQL Queries

    @Query("SELECT u FROM UserEntry u WHERE u.name=:name")
    List<UserEntry>searchByName(@Param("name") String name );

    @Query("SELECT u FROM UserEntry u WHERE u.email=:email")
    Optional<UserEntry>searchByEmail(@Param("email") String email );

    //Native SQL Queries

    @Query(value = "SELECT * FROM user_info WHERE name = :name",
            nativeQuery = true)
    List<UserEntry> findByNameNative(@Param("name") String name);

    @Query(value = "SELECT * FROM user_info WHERE email = :email",
            nativeQuery = true)
    Optional<UserEntry> findByEmailNative(@Param("email") String email);
}
