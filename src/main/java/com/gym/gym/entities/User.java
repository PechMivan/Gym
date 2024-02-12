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
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 10, nullable = false)
    private String password;

    @Column(name="active", nullable = false)
    private boolean isActive;

    @OneToOne(mappedBy = "user")
    private com.gym.gym.entities.Trainer trainer;

    @OneToOne(mappedBy = "user")
    private com.gym.gym.entities.Trainer trainee;

}
