package com.gym.gym.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
    private String dateOfBirth;
    private String address;
}
