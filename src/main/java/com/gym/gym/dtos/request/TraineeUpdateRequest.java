package com.gym.gym.dtos.request;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.validators.OnlyLetters;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraineeUpdateRequest {

    @NotBlank(message = "firstname cannot be null or blank.")
    @OnlyLetters(message = "firstname cannot contain number or symbols.")
    public String firstname;
    @NotBlank(message = "lastname cannot be null or blank.")
    @OnlyLetters(message = "lastname cannot contain number or symbols.")
    public String lastname;
    public String dateOfBirth;
    public String address;
    public boolean active;
    @NotNull(message = "Missing credentials for authorization.")
    @Valid
    public Credentials credentials;
}
