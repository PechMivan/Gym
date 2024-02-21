package com.gym.gym.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@SuppressWarnings("unused")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
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

    @Column(length = 10, nullable = false)
    private String password;

    @Column(name="active", nullable = false)
    private boolean isActive;

}
