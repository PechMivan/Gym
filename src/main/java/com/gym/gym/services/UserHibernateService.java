package com.gym.gym.services;

import com.gym.gym.dtos.UserDTO;
import com.gym.gym.entities.User;

import java.util.List;

public interface UserHibernateService {
    public List<User> getAllUsers();
    public User getUserByUsername(String username);
    public User createUser(UserDTO userData);

    User updateUser(UserDTO userData);

    public void saveUser(User user);
    public String createPassword();
    public String createUsername(String firstname, String lastname);
    public boolean authenticateUser(String username, String password);
    public void changePassword(String username, String oldPassword, String newPassword);
}
