package com.gym.gym.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@Entity
public class Trainee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.DATE)
    @Column
    private Date dateOfBirth;

    @Column(length = 100)
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(name="trainer_trainee",
            joinColumns=@JoinColumn(name="trainer_id"),
            inverseJoinColumns=@JoinColumn(name="trainee_id"))
    Set<Trainer> trainers = new HashSet<>();
}
