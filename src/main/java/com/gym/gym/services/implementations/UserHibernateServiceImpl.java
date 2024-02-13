package com.gym.gym.services.implementations;

import com.gym.gym.dtos.UserDTO;
import com.gym.gym.entities.User;
import com.gym.gym.repositories.UserRepository;
import com.gym.gym.services.UserHibernateService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserHibernateServiceImpl implements UserHibernateService {

    @Autowired
    UserRepository userRepository;

    Random random = new Random();

    @Override
    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUserById(long id){
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User createUser(UserDTO userData) {
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
        //log success
        return  newUser;
    }

    @Override
    public User updateUser(UserDTO userData){
        User existingUser = getUserByUsername(userData.username);

        if(existingUser == null){
            return null;
        }

        User updatedUser = User.builder()
                .id(existingUser.getId())
                .firstName(userData.firstname)
                .lastName(userData.lastname)
                .username(existingUser.getUsername())
                .password(existingUser.getPassword())
                .isActive(userData.isActive)
                .build();

        saveUser(updatedUser);
        //log success
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

        //log success
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
        //log success
        return username.toString();
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        User existingUser = getUserByUsername(username);
        if(existingUser.getUsername() != null){
            return password.equals(existingUser.getPassword());
        }
        return false;
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        if(!authenticateUser(username, oldPassword)){
            return false;
        }
        User existingUser = getUserByUsername(username);
        existingUser.setPassword(newPassword);
        saveUser(existingUser);
        return true;
    }

    @Override
    public Boolean toggleActive(long id){
        User existingUser = getUserById(id);
        if(existingUser == null){
            return null;
        }
        // Toggles the state of active from true to false and viceversa.
        boolean activeState = !existingUser.isActive();
        existingUser.setActive(activeState);
        saveUser(existingUser);
        return activeState;
    }
}
