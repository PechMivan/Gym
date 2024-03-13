package com.gym.gym.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeTrainingFindResponse {
    public String name;
    public String date;
    public String trainingTypeName;
    public String duration;
    public String trainerName;
}
