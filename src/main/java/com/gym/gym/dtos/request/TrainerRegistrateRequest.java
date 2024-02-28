package com.gym.gym.dtos.request;

import com.gym.gym.validators.OnlyLetters;
import jakarta.validation.constraints.NotBlank;

public class TrainerRegistrateRequest {
    @NotBlank(message = "firstname cannot be null or blank.")
    @OnlyLetters(message = "firstname cannot contain number or symbols.")
    public String firstname;
    @NotBlank(message = "lastname cannot be null or blank.")
    @OnlyLetters(message = "lastname cannot contain number or symbols.")
    public String lastname;
    @NotBlank(message = "specialization cannot be null or blank.")
    public String specialization;
}
