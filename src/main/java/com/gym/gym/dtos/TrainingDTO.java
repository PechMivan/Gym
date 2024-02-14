package com.gym.gym.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TrainingDTO {
    public long traineeId;
    public long trainerId;
    public long trainingTypeId;
    public String trainingName;
    public String trainingDate;
    public float trainingDuration;
}
