package com.siemens.useraccountapi.exceptions;

public class JwtTokenMalformedException  extends RuntimeException{
    public JwtTokenMalformedException(String message) {
        super(message);
    }
}
