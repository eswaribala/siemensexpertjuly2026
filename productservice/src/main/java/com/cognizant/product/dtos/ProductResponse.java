package com.cognizant.product.dtos;

public record ProductResponse(long productId, String productName, 
		String description, double price, String purchaseDate, String expiryDate) {

}
