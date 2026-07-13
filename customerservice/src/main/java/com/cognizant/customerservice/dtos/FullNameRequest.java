package com.cognizant.customerservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullNameRequest {
	@NotEmpty(message = "First name must not be empty")
	@NotBlank(message = "First name must not be blank")
	@Pattern(regexp = "^[a-zA-Z]{3,20}$", message = "First name must contain only letters")
	private String firstName;
	@NotEmpty(message = "Last name must not be empty")
	@NotBlank(message = "Last name must not be blank")
	@Pattern(regexp = "^[a-zA-Z]{3,20}$", message = "Last name must contain only letters")

	private String lastName;

}
