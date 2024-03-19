package com.gym.gym.exceptions.customExceptions;

public class RestApiClientException extends RuntimeException{

    public RestApiClientException(String message){
        super(message);
    }
}
