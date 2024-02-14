package com.gym.gym.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDTO {
    @NotNull(message = "User Data cannot be null. ")
    public UserDTO userDTO;
    @NotBlank(message = "Specialization cannot be null or blank. ")
    public String specialization;
}
