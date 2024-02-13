package com.gym.gym.services;

import com.gym.gym.dtos.UserDTO;
import com.gym.gym.entities.User;

import java.util.List;

public interface UserHibernateService {
    List<User> getAllUsers();

    User getUserById(long id);

    User getUserByUsername(String username);
    User createUser(UserDTO userData);
    User updateUser(UserDTO userData);
    void saveUser(User user);
    String createPassword();
    String createUsername(String firstname, String lastname);
    boolean authenticateUser(String username, String password);
    boolean changePassword(String username, String oldPassword, String newPassword);
    Boolean toggleActive(long id);
}
