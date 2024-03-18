package com.gym.gym.dtos.request;

import com.gym.gym.validators.CheckDateFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingCreateRequest {
    @NotBlank(message = "trainee username cannot be null or blank.")
    public String traineeUsername;
    @NotBlank(message = "trainer username cannot be null or blank.")
    public String trainerUsername;
    @NotBlank(message = "training name cannot be null or blank.")
    public String name;
    @NotBlank(message = "training date cannot be null or blank.")
    @CheckDateFormat
    public String date;
    @Positive(message = "training duration must be a positive number")
    @Max(value = 8, message = "duration cannot exceed 8 hours.")
    public int duration;
}
