package com.gym.gym.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDTO {
    @NotNull(message = "traineeId cannot be null. ")
    @Positive(message = "traineeId must be positive. ")
    public long traineeId;
    @NotNull(message = "trainerId cannot be null. ")
    @Positive(message = "trainerId must be positive. ")
    public long trainerId;
    @NotNull(message = "trainingTypeId cannot be null. ")
    @Positive(message = "trainingTypeId must be positive. ")
    public long trainingTypeId;
    @NotBlank(message = "trainingName cannot be null or blank. ")
    public String trainingName;
    @NotBlank(message = "trainingDate cannot be null or blank. ")
    public String trainingDate;
    @Positive(message = "trainingDuration must be a positive value. ")
    public float trainingDuration;
}
