package com.gym.gym.exceptions.customExceptions;

public class MaxLoginAttemptsException extends RuntimeException{

    public MaxLoginAttemptsException(String message){
        super(message);
    }
}
