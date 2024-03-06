package com.gym.gym.dtos.request;

import com.gym.gym.dtos.Credentials;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveStateChangeRequest {
    @NotBlank(message = "username cannot be null or blank.")
    public String username;
    public boolean active;
}
