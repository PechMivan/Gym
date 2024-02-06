package com.gym.gym.entities;

import lombok.*;

@Getter
@Setter
@SuppressWarnings("unused")
public abstract class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;

}
