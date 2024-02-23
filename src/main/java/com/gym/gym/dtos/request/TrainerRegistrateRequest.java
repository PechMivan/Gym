package com.gym.gym.dtos.request;

import jakarta.validation.constraints.NotBlank;

public class TrainerRegistrateRequest {
    @NotBlank(message = "firstname cannot be null or blank.")
    public String firstname;
    @NotBlank(message = "lastname cannot be null or blank.")
    public String lastname;
    @NotBlank(message = "specialization cannot be null or blank.")
    public String specialization;
}
