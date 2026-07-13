package com.cognizant.customerservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cognizant.customerservice.dtos.CustomerRequest;
import com.cognizant.customerservice.dtos.CustomerResponse;
import com.cognizant.customerservice.entities.Customer;

@Mapper(componentModel = "spring", uses = {FullNameMapper.class})
public interface CustomerMapper {
	
	@Mapping(target = "fullName", source = "customerRequest.fullNameRequest")
	//dtos to entity
	Customer toEntity(CustomerRequest customerRequest);
	//entity to dtos
	CustomerResponse toRequest(Customer customer);

}
