package com.gym.gym.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
    private String specialization;
}
