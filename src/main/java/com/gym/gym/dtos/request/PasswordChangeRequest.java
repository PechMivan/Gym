package com.gym.gym.dtos.request;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@SuppressWarnings("unused")
public class PasswordChangeRequest {
    public String username;
    public String oldPassword;
    public String newPassword;
}
