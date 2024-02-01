package com.gym.gym.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Training {
    private long traineeId;
    private long trainerId;
    private String trainingName;
    private TrainingType trainingType;
    private Date trainingDate;
    private int trainingDuration;
}
