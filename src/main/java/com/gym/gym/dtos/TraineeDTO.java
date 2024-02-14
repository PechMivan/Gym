package com.gym.gym.dtos;

import com.gym.gym.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeDTO {
    @NotNull(message = "User Data cannot be null.")
    public UserDTO userDTO;
    public String dateOfBirth;
    public String address;
}
