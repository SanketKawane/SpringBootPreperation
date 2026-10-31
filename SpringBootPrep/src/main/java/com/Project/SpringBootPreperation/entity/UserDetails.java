package com.Project.SpringBootPreperation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "USER";
    public UserDetails(){}
    public UserDetails(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
