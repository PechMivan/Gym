package com.gym.gym.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsAndAccessToken {
    @Valid
    Credentials credentials;
    @NotBlank(message = "accessToken cannot be null or blank.")
    String accessToken;
}
