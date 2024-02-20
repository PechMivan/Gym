package com.gym.gym.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingCreateRequest {
    public String traineeUsername;
    public String trainerUsername;
    public String trainingName;
    public String trainingDate;
    public int trainingDuration;
}
