package com.gym.gym.dtos.request;

import com.gym.gym.dtos.Credentials;
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
    public Credentials credentials;
}
