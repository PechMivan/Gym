package com.gym.gym.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDTO {
    public String username;
    public String firstname;
    public String lastname;
    public String specialization;
}
