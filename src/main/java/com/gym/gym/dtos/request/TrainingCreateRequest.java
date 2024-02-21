package com.gym.gym.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingCreateRequest {
    public String traineeUsername;
    public String trainerUsername;
    public String name;
    public String date;
    public int duration;
}
