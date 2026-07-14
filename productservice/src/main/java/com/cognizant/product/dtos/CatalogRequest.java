package com.cognizant.product.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogRequest {
	@NotNull(message = "Catalog name cannot be null")
	@NotEmpty(message = "Catalog name cannot be empty")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Catalog name must be alphabets only")
	private String catalogName;

}
