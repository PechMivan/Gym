package com.gym.gym.services;

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
    boolean changePassword(String username, String oldPassword, String newPassword);
    void validatePassword(String newPassword);
    Boolean changeActiveState(String username, boolean activeState);
}
