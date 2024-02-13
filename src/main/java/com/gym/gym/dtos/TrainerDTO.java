package com.gym.gym.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDTO {
    public UserDTO userDTO;
    public String specialization;
}
