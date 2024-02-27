package com.gym.gym.services.implementations;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.InvalidPasswordException;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.exceptions.UnauthorizedAccessException;
import com.gym.gym.repositories.UserRepository;
import com.gym.gym.services.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    Random random = new Random();

    @Override
    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUserById(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d not found", id)));
    }

    @Override
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("User with username %s not found", username)));
    }

    @Override
    public User createUser(User user) {
        String newUsername = createUsername(user.getFirstname(), user.getLastname());
        String newPassword = createPassword();

        User newUser = User.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(newUsername)
                .password(newPassword)
                .isActive(true)
                .build();

        saveUser(newUser);
        log.info(String.format("User successfully created with id: %d", newUser.getId()));
        return  newUser;
    }

    @Override
    public User updateUser(String username, User user){
        //authenticateUser(user.getUsername(), user.getPassword());
        User existingUser = getUserByUsername(username);

        User updatedUser = User.builder()
                .id(existingUser.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(existingUser.getUsername())
                .password(existingUser.getPassword())
                .isActive(user.isActive())
                .build();

        saveUser(updatedUser);
        log.info(String.format("User with id %d successfully updated", updatedUser.getId()));
        return updatedUser;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public String createPassword(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 10;

        //Gets a character from characters Strings based on the random number generated.
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    @Override
    public String createUsername(String firstname, String lastname) {
        StringBuilder username = new StringBuilder();
        username.append(firstname.toLowerCase());
        username.append(".");
        username.append(lastname.toLowerCase());

        //Finds all usernames with the same firstname and lastname.
        List<String> existingUsernames = getAllUsers().stream()
                .filter(user -> (
                        user.getFirstname().equalsIgnoreCase(firstname)
                        && user.getLastname().equalsIgnoreCase(lastname)
                )).map(User::getUsername)
                .toList();

        int baseId = 1;
        String originalUsername = username.toString();
        while (existingUsernames.contains(username.toString())) {
            username.setLength(originalUsername.length()); // Reset to original length
            username.append(baseId);
            baseId++;
        }

        return username.toString();
    }

    @Override
    public void authenticateUser(String username, String password) {
        User existingUser = getUserByUsername(username);
        if(!password.equals(existingUser.getPassword())){
            log.error("Unauthorized login attempt");
            throw new UnauthorizedAccessException("Invalid login attempt: Password or username don't match.");
        }
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        authenticateUser(username, oldPassword);
        validatePassword(newPassword);
        User existingUser = getUserByUsername(username);
        existingUser.setPassword(newPassword);
        saveUser(existingUser);
        log.info(String.format("Password successfully changed for user with id %d", existingUser.getId()));
        return true;
    }

    @Override
    public void validatePassword(String newPassword){
        if(newPassword == null || newPassword.isEmpty()){
            log.error("New Password cannot be null or blank.");
            throw new InvalidPasswordException("New Password cannot be null or blank.");
        }
        if(newPassword.length() < 8 || newPassword.length() > 10){
            log.error("New Password should contain at least 8 and no more than 10 characters");
            throw new InvalidPasswordException("New Password should contain at least 8 and no more than 10 characters");
        }
    }

    //TODO: Modify unit testing.
    @Override
    public Boolean changeActiveState(String username, boolean activeState, Credentials credentials){
        authenticateUser(credentials.username, credentials.password);
        User existingUser = getUserByUsername(username);
        existingUser.setActive(activeState);
        saveUser(existingUser);
        log.info(String.format("Active status successfully changed for user with id %d", existingUser.getId()));
        return activeState;
    }
}
