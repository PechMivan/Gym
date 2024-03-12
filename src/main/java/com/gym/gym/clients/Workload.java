package com.gym.gym.clients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Workload {
    public String username;
    public String firstname;
    public String lastname;
    public boolean isActive;
    public String trainingDate;
    public int trainingDuration;
    public String actionType;
}
