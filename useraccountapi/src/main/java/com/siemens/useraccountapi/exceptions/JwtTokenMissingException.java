package com.siemens.useraccountapi.exceptions;

public class JwtTokenMissingException extends RuntimeException{
    public JwtTokenMissingException(String message) {
        super(message);
    }
}
