package com.gym.gym.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraineeUpdateRequest {

    public String firstname;
    public String lastname;
    public String dateOfBirth;
    public String address;
    public boolean active;
}
