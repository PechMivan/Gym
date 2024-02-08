package com.gym.gym.services.implementations;

import com.gym.gym.daos.implementations.TrainerDAOImpl;
import com.gym.gym.entities.Trainer;
import com.gym.gym.services.TrainerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.*;

@Component
public class TrainerServiceImpl implements TrainerService {

    Random random = new Random();
    private TrainerDAOImpl trainerDAO;

    @Autowired
    public void setTrainerDAOImpl(TrainerDAOImpl trainerDAO){
        this.trainerDAO = trainerDAO;
    }

    @Override
    public Trainer getTrainerById(long id) {
        return trainerDAO.findById(id)
                .orElseThrow(() -> new IllegalStateException("Trainer doesn't exist with id: " + id));
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerDAO.findAll();
    }

    @Override
    public Trainer createTrainer(Trainer trainer){
        trainer.setUsername(createUsername(trainer.getFirstName(), trainer.getLastName()));
        trainer.setPassword(createPassword());
        saveTrainer(trainer);
        return trainer;
    }

    @Override
    public void saveTrainer(Trainer trainer) {
        trainerDAO.save(trainer);
    }

    @Override
    public void updateTrainer(long id, Trainer updatedTrainer) {
        Trainer existingTrainer = getTrainerById(id);

        BeanUtils.copyProperties(updatedTrainer, existingTrainer, getNullPropertyNames(updatedTrainer));

        saveTrainer(existingTrainer);
    }

    @Override
    public void deleteTrainer(long id) {
        trainerDAO.delete(id);
    }

    public String createUsername(String firstname, String lastname){
        StringBuilder username = new StringBuilder();
        username.append(firstname);
        username.append(".");
        username.append(lastname);

        long repeatedUsernameSize = getAllTrainers().stream()
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
