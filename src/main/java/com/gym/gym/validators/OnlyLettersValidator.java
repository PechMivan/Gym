package com.gym.gym.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OnlyLettersValidator implements ConstraintValidator<OnlyLetters, String> {

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if ( object == null || object.isBlank() ) {
            return true;
        }

        //Validates that String contains no numbers or symbols.
        String regex = "^\\p{L}+$";

        return object.matches(regex);
    }
}