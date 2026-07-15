package com.boa.userservice.exceptions;

public class UserNotFoundExcepion extends RuntimeException{
    public UserNotFoundExcepion(String message) {
        super(message);
    }
}
