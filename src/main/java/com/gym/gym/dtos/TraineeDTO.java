package com.gym.gym.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TraineeDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
    private String dateOfBirth;
    private String address;
}
