package com.gym.gym.dtos.request;

import com.gym.gym.validators.CheckDateFormat;
import com.gym.gym.validators.OnlyLetters;
import jakarta.validation.constraints.NotBlank;

public class TraineeRegistrateRequest {
    @NotBlank(message = "firstname cannot be null or blank.")
    @OnlyLetters(message = "firstname cannot contain number or symbols.")
    public String firstname;
    @NotBlank(message = "lastname cannot be null or blank.")
    @OnlyLetters(message = "lastname cannot contain number or symbols.")
    public String lastname;
    @CheckDateFormat
    public String dateOfBirth;
    public String address;
}
