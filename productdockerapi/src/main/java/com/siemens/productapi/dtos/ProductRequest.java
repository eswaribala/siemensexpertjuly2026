package com.siemens.productapi.dtos;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotNull(message = "Product name cannot be null")
    @NotEmpty(message = "Product name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Product name must be alphabets")
    private String productName;
    @PastOrPresent(message = "Date of purchase cannot be in the future")
    private LocalDate dop;
    @Min(value = 0, message = "Price cannot be negative")
    @Max(value = 1000000, message = "Price cannot exceed 1,000,000")
    private double price;

}
