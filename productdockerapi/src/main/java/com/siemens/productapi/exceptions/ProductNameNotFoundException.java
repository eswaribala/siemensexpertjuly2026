package com.siemens.productapi.exceptions;

public class ProductNameNotFoundException extends RuntimeException {

    public ProductNameNotFoundException(String message) {
        super(message);
    }
}
