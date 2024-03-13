package com.gym.gym.dtos.request;

import com.gym.gym.validators.CheckDateFormat;
import com.gym.gym.validators.OnlyLetters;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class TraineeTrainingFindRequest {
    @NotBlank(message = "username cannot be null or blank.")
    public String username;
    @CheckDateFormat
    public String periodFrom;
    @CheckDateFormat
    public String periodTo;
    @OnlyLetters(message = "trainer name cannot contain number or symbols")
    public String trainerName;
    public String trainingTypeName;
}
