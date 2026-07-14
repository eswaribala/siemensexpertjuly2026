package com.cognizant.product.exceptions;

public class ProductNotFoundException extends Exception {
	

	public ProductNotFoundException(String message) {
		super(message); //Throwable class that contains a message string that describes the exception. The message can be retrieved later by the getMessage() method.
	}

}
