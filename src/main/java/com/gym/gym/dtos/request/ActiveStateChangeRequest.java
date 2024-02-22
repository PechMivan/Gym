package com.gym.gym.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveStateChangeRequest {
    public String username;
    public boolean active;
}
