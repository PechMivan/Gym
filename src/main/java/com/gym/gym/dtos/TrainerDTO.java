package com.gym.gym.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainerDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
    private String specialization;
}
