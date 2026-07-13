package com.cognizant.customerservice.dtos;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.javafaker.Faker;

public class TestCustomerRequest {
	private static CustomerRequest customerRequest;
	private static FullNameRequest fullNameRequest;
	
	@BeforeAll
	public static void getCustomerRequest() {
		customerRequest = new CustomerRequest();
		fullNameRequest = new FullNameRequest();
	}

	@Test
	@Tag("dev")
	public void testCustomerRequestNotNull() {
		assertNotNull(customerRequest);
	}
	
	@ParameterizedTest
	@Tag("dev")
	@CsvFileSource(resources = "/customerdata.csv", numLinesToSkip = 1)
	public void testCustomerRequestProperties(String firstName, String lastName, String email, long contactNo, String password) {
		fullNameRequest.setFirstName(firstName);
		fullNameRequest.setLastName(lastName);
		customerRequest.setFullNameRequest(fullNameRequest);
		customerRequest.setEmail(email);
		customerRequest.setContactNo(contactNo);
		customerRequest.setPassword(password);
		assertTrue(customerRequest.getFullNameRequest()
				.getFirstName().equals(firstName));	
		assertAll(() -> assertTrue(customerRequest.getFullNameRequest()
				.getLastName().equals(lastName)),
				() -> assertTrue(customerRequest.getEmail().equals(email)),
				() -> assertTrue(customerRequest.getContactNo() == contactNo),
				() -> assertTrue(customerRequest.getPassword().equals(password)));		
	}
	
	@ParameterizedTest
	@Tag("qa")
	@ValueSource(strings = {"Mithun", "Rakesh", "Swathi"})
	public void testCustomerRequestFirstName(String name) {
		fullNameRequest.setFirstName(name);
		customerRequest.setFullNameRequest(fullNameRequest);
		assertNotNull(customerRequest.getFullNameRequest().getFirstName());
	}
	
	@Nested
	class TestCustomerRequestInner {
		@ParameterizedTest
		@Tag("qa")
		@MethodSource("com.cognizant.customerservice.dtos.TestCustomerRequest#getCustomerRequestList")
		public void testCustomerRequestPropertiesUsingMethodSource(CustomerRequest customerRequest) {
			fullNameRequest.setFirstName(customerRequest.getFullNameRequest().getFirstName());
			fullNameRequest.setLastName(customerRequest.getFullNameRequest().getLastName());
			customerRequest.setFullNameRequest(fullNameRequest);
			customerRequest.setEmail(customerRequest.getEmail());
			customerRequest.setContactNo(customerRequest.getContactNo());
			customerRequest.setPassword(customerRequest.getPassword());
			assertTrue(customerRequest.getFullNameRequest()
					.getFirstName().equals(fullNameRequest.getFirstName()));	
			assertAll(() -> assertTrue(customerRequest.getFullNameRequest()
					.getLastName().equals(fullNameRequest.getLastName())),
					() -> assertTrue(customerRequest.getEmail().equals(customerRequest.getEmail())),
					() -> assertTrue(customerRequest.getContactNo() == customerRequest.getContactNo()),
					() -> assertTrue(customerRequest.getPassword().equals(customerRequest.getPassword())));		
		}
	}
	
	
	
	
	
	private static Stream<Arguments> getCustomerRequestList() {
		
		List<Arguments> list = new ArrayList<>();
		Faker faker = new Faker();
		for(int i=0; i<5; i++) {
			FullNameRequest fullNameRequest = new FullNameRequest();
			CustomerRequest customerRequest = new CustomerRequest();
			fullNameRequest.setFirstName(faker.name().firstName());
			fullNameRequest.setLastName(faker.name().lastName());
			customerRequest.setFullNameRequest(fullNameRequest);
			customerRequest.setEmail(faker.internet().emailAddress());
			customerRequest.setContactNo(faker.number().numberBetween(1000000000L, 9999999999L));
			customerRequest.setPassword(faker.internet().password());
			list.add(Arguments.of(customerRequest));
		}
		return list.stream();
		
	}
	
}
