package com.gym.gym.dtos.request;

import jakarta.validation.constraints.NotBlank;

public class TraineeRegistrateRequest {
    @NotBlank(message = "firstname cannot be null or blank.")
    public String firstname;
    @NotBlank(message = "lastname cannot be null or blank.")
    public String lastname;
    public String dateOfBirth;
    public String address;
}
