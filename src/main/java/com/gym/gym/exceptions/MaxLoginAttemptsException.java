package com.gym.gym.exceptions;

public class MaxLoginAttemptsException extends RuntimeException{

    public MaxLoginAttemptsException(String message){
        super(message);
    }
}
