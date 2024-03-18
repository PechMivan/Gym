package com.gym.gym.dtos.request;

import com.gym.gym.dtos.Credentials;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainersListUpdateRequest {
    @NotNull(message = "usernames cannot be null.")
    public List<
            @NotBlank(message = "username cannot be null or blank.")
            String>
            usernames;
}
