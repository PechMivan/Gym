package com.gym.gym.services;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(long id);

    User getUserByUsername(String username);
    User createUser(User user);
    User updateUser(String username, User user);
    void saveUser(User user);
    String createPassword();
    String createUsername(String firstname, String lastname);
    void authenticateUser(String username, String password);
    boolean changePassword(String username, String oldPassword, String newPassword);
    void validatePassword(String newPassword);
    Boolean changeActiveState(String username, boolean activeState, Credentials credentials);
}
