package com.gym.gym.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuppressWarnings("unused")
@SuperBuilder
@NoArgsConstructor
public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;

}
