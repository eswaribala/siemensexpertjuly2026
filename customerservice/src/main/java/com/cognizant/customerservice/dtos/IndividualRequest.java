package com.cognizant.customerservice.dtos;
import java.time.LocalDate;
import com.cognizant.customerservice.entities.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IndividualRequest extends CustomerRequest {
	
	private Gender gender;
	private LocalDate dateOfBirth;

}
