package com.gym.gym.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDTO {
    @NotBlank(message = "firstname cannot be null or blank. ")
    public String firstname;
    @NotBlank(message = "lastname cannot be null or blank. ")
    public String lastname;
    public String username;
    public String password;
    public boolean isActive;
}
