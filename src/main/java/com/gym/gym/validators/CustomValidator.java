package com.gym.gym.validators;

import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.InvalidAddressException;
import com.gym.gym.exceptions.InvalidNameException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@SuppressWarnings("unused")
public class CustomValidator {

    public void validateUserData(User user){
        List<String> names = new ArrayList<>(Arrays.asList(user.getFirstname(), user.getLastname()));
        validateNames(names);
    }

    public void validateNames(List<String> names){
        for(String name : names) {
            if (name == null) {
                throw new InvalidNameException("Names cannot be null.");
            }
            if (name.isBlank()) {
                throw new InvalidNameException("Names cannot be empty or contain only whitespaces.");
            }
            if (!containsOnlyLetters(name)) {
                throw new InvalidNameException("Names must contain only letters.");
            }
        }
    }

    public void validateTraineeData(Trainee trainee){
        if(trainee.getAddress() == null){
            throw new InvalidAddressException("Address cannot be null.");
        }
    }

    public void validateDate(String stringDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate date = LocalDate.parse(stringDate, formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
    }

    public boolean containsOnlyLetters(String input) {
        // Regular expression to match only letters (a-z or A-Z)
        return input.matches("[a-zA-Z]+");
    }
}
