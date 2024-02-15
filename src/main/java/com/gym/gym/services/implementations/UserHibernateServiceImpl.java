package com.gym.gym.services.implementations;

import com.gym.gym.dtos.UserDTO;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.InvalidPasswordException;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.exceptions.UnauthorizedAccessException;
import com.gym.gym.repositories.UserRepository;
import com.gym.gym.services.UserHibernateService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class UserHibernateServiceImpl implements UserHibernateService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    Validator validator;

    Logger logger = LoggerFactory.getLogger(UserHibernateServiceImpl.class);

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
    public User createUser(UserDTO userData) {
        validateData(userData);
        String newUsername = createUsername(userData.firstname, userData.lastname);
        String newPassword = createPassword();

        User newUser = User.builder()
                .firstName(userData.firstname)
                .lastName(userData.lastname)
                .username(newUsername)
                .password(newPassword)
                .isActive(userData.isActive)
                .build();

        saveUser(newUser);
        logger.info(String.format("User successfully created with id: %d", newUser.getId()));
        return  newUser;
    }

    @Override
    public User updateUser(UserDTO userData){
        authenticateUser(userData.username, userData.password);
        validateData(userData);
        User existingUser = getUserByUsername(userData.username);

        User updatedUser = User.builder()
                .id(existingUser.getId())
                .firstName(userData.firstname)
                .lastName(userData.lastname)
                .username(existingUser.getUsername())
                .password(existingUser.getPassword())
                .isActive(userData.isActive)
                .build();

        saveUser(updatedUser);
        logger.info(String.format("User with id %d successfully updated", updatedUser.getId()));
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
        username.append(firstname);
        username.append(".");
        username.append(lastname);

        //Finds all usernames with the same username (ignoring suffix) and counts them.
        long repeatedUsernameSize = getAllUsers().stream()
                .filter(user -> user.getUsername().toLowerCase().contains(username.toString().toLowerCase()))
                .count();

        if(repeatedUsernameSize > 0){
            username.append(repeatedUsernameSize);
        }
        return username.toString();
    }

    @Override
    public void authenticateUser(String username, String password) {
        User existingUser = getUserByUsername(username);
        if(!password.equals(existingUser.getPassword())){
            logger.error("Unauthorized login attempt");
            throw new UnauthorizedAccessException("Invalid login attempt: Password or username don't match.");
        }
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        validatePassword(newPassword);
        authenticateUser(username, oldPassword);
        User existingUser = getUserByUsername(username);
        existingUser.setPassword(newPassword);
        saveUser(existingUser);
        logger.info(String.format("Password successfully changed for user with id %d", existingUser.getId()));
        return true;
    }

    @Override
    public void validatePassword(String newPassword){
        if(newPassword == null || newPassword.isEmpty()){
            logger.error("New Password cannot be null or blank.");
            throw new InvalidPasswordException("New Password cannot be null or blank.");
        }
        if(newPassword.length() < 8 || newPassword.length() > 10){
            logger.error("New Password should contain at least 8 and no more than 10 characters");
            throw new InvalidPasswordException("New Password should contain at least 8 and no more than 10 characters");
        }
    }

    @Override
    public Boolean toggleActive(long id){
        User existingUser = getUserById(id);
        // Toggles the state of active from true to false and viceversa.
        boolean activeState = !existingUser.isActive();
        existingUser.setActive(activeState);
        saveUser(existingUser);
        logger.info(String.format("Active status successfully change for user with id %d", existingUser.getId()));
        return activeState;
    }

    public void validateData(UserDTO userData){
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userData);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<UserDTO> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
    }
}
