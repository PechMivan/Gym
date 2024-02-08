package com.gym.gym.services.implementations;

import com.gym.gym.daos.implementations.TraineeDAOImpl;
import com.gym.gym.entities.Trainee;
import com.gym.gym.services.TraineeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.*;

@Component
public class TraineeServiceImpl implements TraineeService {

    Random random = new Random();

    private TraineeDAOImpl traineeDAO;

    @Autowired
    public void setTraineeDAOImpl(TraineeDAOImpl traineeDAO){
        this.traineeDAO = traineeDAO;
    }

    @Override
    public Trainee getTraineeById(long id) {
        return traineeDAO.findById(id)
                .orElseThrow(() -> new IllegalStateException("Trainee doesn't exist with id: " + id));
    }

    @Override
    public List<Trainee> getAllTrainees() {
        return traineeDAO.findAll();
    }

    @Override
    public Trainee createTrainee(Trainee trainee){
        trainee.setUsername(createUsername(trainee.getFirstName(), trainee.getLastName()));
        trainee.setPassword(createPassword());
        saveTrainee(trainee);
        return trainee;
    }

    @Override
    public void saveTrainee(Trainee trainee) {
        traineeDAO.save(trainee);
    }

    @Override
    public void updateTrainee(long id, Trainee updatedTrainee) {
        Trainee existingTrainee = getTraineeById(id);

        BeanUtils.copyProperties(updatedTrainee, existingTrainee, getNullPropertyNames(updatedTrainee));

        saveTrainee(existingTrainee);
    }

    @Override
    public void deleteTrainee(long id) {
        traineeDAO.delete(id);
    }

    public String createUsername(String firstname, String lastname){
        StringBuilder username = new StringBuilder();
        username.append(firstname);
        username.append(".");
        username.append(lastname);

        long repeatedUsernameSize = getAllTrainees().stream()
                .filter(trainee -> trainee.getUsername().toLowerCase().contains(username.toString().toLowerCase()))
                .count();

        if(repeatedUsernameSize > 0){
            username.append(repeatedUsernameSize);
        }
        return username.toString();
    }

    public String createPassword(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 10;

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    private String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
