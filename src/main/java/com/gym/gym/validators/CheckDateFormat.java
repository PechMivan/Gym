package com.gym.gym.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckDateFormatValidator.class)
@Documented
@SuppressWarnings("unused")
public @interface CheckDateFormat {
    String message() default "Invalid date format";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String format() default "yyyy-MM-dd";
}
