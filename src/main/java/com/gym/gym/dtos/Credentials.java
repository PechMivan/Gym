package com.gym.gym.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {
    @NotBlank(message = "username cannot be null or blank")
    public String username;
    @NotBlank(message = "password cannot be null or blank")
    public String password;
    @NotBlank(message = "accesstoken cannot be null or blank")
    public String accessToken;
}
