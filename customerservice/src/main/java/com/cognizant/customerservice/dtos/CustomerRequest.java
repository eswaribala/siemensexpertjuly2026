package com.cognizant.customerservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
	//@Schema(hidden = true)
	//private long accountNo;
	private FullNameRequest fullNameRequest;
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
	private String email;
	private long contactNo;
	//password with special characters and minimum 8 characters
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
		 message = "Password must be minimum 8 characters, at least one uppercase letter, one lowercase letter, one number and one special character")
	private String password;
	

}
