package com.cognizant.product.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
	@NotNull(message = "Product name cannot be null")
	@NotEmpty(message = "Product name cannot be empty")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Product name must be alphabets only")
	private String productName;	
	private String description;    
	@Min(value = 0, message = "Price cannot be negative")
	@Max(value = 1000000, message = "Price cannot exceed 1,000,000")
	private double price;
	@PastOrPresent(message = "Purchase date cannot be in the future")
	private LocalDate purchaseDate;
	@Future(message = "Expiry date must be in the future")
	private LocalDate expiryDate;
	
}
