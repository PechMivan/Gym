package com.gym.gym.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Training implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="trainingTypeId", nullable=false)
    private com.gym.gym.entities.TrainingType trainingType;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private int duration;

    @ManyToOne
    @JoinColumn(name="traineeId", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private com.gym.gym.entities.Trainee trainee;

    @ManyToOne
    @JoinColumn(name="trainerId", nullable=false)
    private com.gym.gym.entities.Trainer trainer;

}
