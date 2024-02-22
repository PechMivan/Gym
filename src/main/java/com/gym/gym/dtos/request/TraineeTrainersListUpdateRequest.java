package com.gym.gym.dtos.request;

import com.gym.gym.dtos.Credentials;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainersListUpdateRequest {
    public List<String> usernames;
    public Credentials credentials;
}
