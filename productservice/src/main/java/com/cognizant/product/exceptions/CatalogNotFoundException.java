package com.cognizant.product.exceptions;

public class CatalogNotFoundException extends Exception{

	public CatalogNotFoundException(String message) {
		super(message); //Throwable class that contains a message string and a cause (another throwable) that can be used to provide more information about the exception.
	}
}
