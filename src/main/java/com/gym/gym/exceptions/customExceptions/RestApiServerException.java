package com.gym.gym.exceptions.customExceptions;

public class RestApiServerException extends RuntimeException{

    public RestApiServerException(String message){
        super(message);
    }
}
