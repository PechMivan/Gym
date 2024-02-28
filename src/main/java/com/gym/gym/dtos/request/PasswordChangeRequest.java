package com.gym.gym.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@SuppressWarnings("unused")
public class PasswordChangeRequest {
    @NotBlank(message = "username cannot be null or blank.")
    public String username;
    @NotBlank(message = "oldPassword cannot be null or blank.")
    public String oldPassword;
    @NotBlank(message = "newPassword cannot be null or blank.")
    public String newPassword;
}
