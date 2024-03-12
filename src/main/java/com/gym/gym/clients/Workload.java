package com.gym.gym.clients;

import com.gym.gym.entities.Training;
import com.gym.gym.helpers.DateHelper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Workload {
    public String username;
    public String firstname;
    public String lastname;
    public boolean isActive;
    public String trainingDate;
    public int trainingDuration;
    public String actionType;

    public static Workload buildWorkload(Training training, String actionType){
        return Workload.builder()
                .username(training.getTrainer().getUser().getUsername())
                .firstname(training.getTrainer().getUser().getFirstname())
                .lastname(training.getTrainer().getUser().getLastname())
                .isActive(training.getTrainer().getUser().isActive())
                .trainingDate(DateHelper.parseDate(training.getDate()))
                .trainingDuration(training.getDuration())
                .actionType(actionType)
                .build();
    }
}
