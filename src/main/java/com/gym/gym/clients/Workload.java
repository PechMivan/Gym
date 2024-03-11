package com.gym.gym.clients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Workload {
    public String username;
    public String firstname;
    public String lastname;
    public boolean isActive;
    public String trainingDate;
    public int trainingDuration;
    public String actionType;
}
