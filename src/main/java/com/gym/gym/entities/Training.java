package com.gym.gym.entities;

import jakarta.persistence.*;
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
    private String trainingName;

    @ManyToOne
    @JoinColumn(name="trainingTypeId", nullable=false)
    private com.gym.gym.entities.TrainingType trainingType;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date trainingDate;

    @Column(nullable = false)
    private int trainingDuration;

    @ManyToOne
    @JoinColumn(name="traineeId", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private com.gym.gym.entities.Trainee trainee;

    @ManyToOne
    @JoinColumn(name="trainerId", nullable=false)
    private com.gym.gym.entities.Trainer trainer;
}
