package com.siemens.useraccountapi.exceptions;

public class UserAccountNotFoundException extends RuntimeException{
    public UserAccountNotFoundException(String message) {
        super(message);
    }
}
