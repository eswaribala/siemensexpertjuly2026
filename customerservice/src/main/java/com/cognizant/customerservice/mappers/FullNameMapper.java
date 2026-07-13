package com.cognizant.customerservice.mappers;

import org.mapstruct.Mapper;

import com.cognizant.customerservice.dtos.FullNameRequest;
import com.cognizant.customerservice.dtos.FullNameResponse;
import com.cognizant.customerservice.entities.FullName;

@Mapper(componentModel = "spring")
public interface FullNameMapper {
	
	//dtos to entity
	FullName toEntity(FullNameRequest fullNameRequest);
	
	//entity to dtos
	FullNameResponse toResponse(FullName fullName);

}
