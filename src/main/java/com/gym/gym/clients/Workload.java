package com.gym.gym.clients;

import com.gym.gym.entities.Training;
import com.gym.gym.helpers.DateHelper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Workload {
    private String username;
    private String firstname;
    private String lastname;
    private boolean isActive;
    private String trainingDate;
    private int trainingDuration;
    private String actionType;

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
