package com.gym.gym.dtos.request;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.validators.OnlyLetters;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TrainerUpdateRequest {
    @NotBlank(message = "firstname cannot be null or blank.")
    @OnlyLetters(message = "firstname cannot contain number or symbols.")
    public String firstname;
    @NotBlank(message = "lastname cannot be blank.")
    @OnlyLetters(message = "lastname cannot contain number or symbols.")
    public String lastname;
    @NotBlank(message = "specialization cannot be blank.")
    public String specialization;
    public String active;
    @NotNull(message = "Missing credentials for authorization.")
    @Valid
    public Credentials credentials;
}
