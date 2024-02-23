package com.gym.gym.dtos.request;

import com.gym.gym.dtos.Credentials;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TrainerUpdateRequest {
    @NotBlank(message = "firstname cannot be blank.")
    public String firstname;
    @NotBlank(message = "lastname cannot be blank.")
    public String lastname;
    @NotBlank(message = "specialization cannot be blank.")
    public String specialization;
    public String active;
    @NotNull(message = "Missing credentials for authorization.")
    @Valid
    public Credentials credentials;
}
