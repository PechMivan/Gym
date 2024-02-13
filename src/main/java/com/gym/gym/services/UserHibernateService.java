package com.gym.gym.services;

import com.gym.gym.dtos.TraineeDTO;
import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.entities.User;

public interface UserHibernateService {
    public User createUser(Object data);
    public String createPassword();
    public String createUsername(String firstname, String lastname);
    public boolean authenticateUser(String username, String password);
    public void changePassword(String username, String oldPassword, String newPassword);
}
