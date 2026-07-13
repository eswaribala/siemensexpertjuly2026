package com.cognizant.customerservice.entities;

import com.cognizant.customerservice.facades.AccountNoAnnotator;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer {
	@Id
	@AccountNoAnnotator
	@Column(name = "account_no")
	@Schema(hidden = true)
	protected Long accountNo;
	@Embedded
	protected FullName fullName;
	@Column(name = "contact_no")
	protected long contactNo;
	@Column(name = "email", unique = true, nullable = false,length = 100)
	protected String email;
	@Column(name = "password", nullable = false,length = 10)
	protected String password;
	


}
