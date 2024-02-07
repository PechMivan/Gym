package com.gym.gym.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class Training implements Serializable {
    private long trainingId;
    private long traineeId;
    private long trainerId;
    private String trainingName;
    private TrainingType trainingType;
    private Date trainingDate;
    private int trainingDuration;

}
