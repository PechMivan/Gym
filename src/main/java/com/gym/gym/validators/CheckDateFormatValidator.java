package com.gym.gym.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CheckDateFormatValidator implements ConstraintValidator<CheckDateFormat, String> {

    private String format;

    @Override
    public void initialize(CheckDateFormat constraintAnnotation) {
        this.format = constraintAnnotation.format();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if ( object == null ) {
            return true;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setLenient(false); // Strict Parsing
        try {
            formatter.parse(object);
            return true; // Parsing succeeded, so the format is valid
        } catch (ParseException e) {
            constraintContext.disableDefaultConstraintViolation(); // Disable default message
            constraintContext.buildConstraintViolationWithTemplate(String.format("Invalid date format. It Should follow: [%s] format", format)).addConstraintViolation(); // Custom message
            return false; // Parsing failed, so the format is invalid
        }
    }
}
