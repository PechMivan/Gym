package com.gym.gym.dtos;

import com.gym.gym.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeDTO {
    public UserDTO userDTO;
    public String dateOfBirth;
    public String address;
}
