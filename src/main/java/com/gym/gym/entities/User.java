package com.gym.gym.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@SuppressWarnings("unused")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String firstname;

    @Column(length = 50, nullable = false)
    private String lastname;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    @Transient
    private String password;

    @Column(length = 60, columnDefinition = "BINARY(60)", nullable = false)
    private String hashedPassword;

    @Column(name="active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Token> tokens;
}
