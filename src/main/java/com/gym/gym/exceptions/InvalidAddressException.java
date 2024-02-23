package com.gym.gym.exceptions;

public class InvalidAddressException extends RuntimeException{
    public InvalidAddressException(String message){
        super(message);
    }
}
