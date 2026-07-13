package com.cognizant.customerservice.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cognizant.customerservice.dtos.IndividualRequest;
import com.cognizant.customerservice.dtos.IndividualResponse;
import com.cognizant.customerservice.entities.Individual;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, FullNameMapper.class})
public interface IndividualMapper {
	//@Mapping(target = "accountNo", source = "individualRequest.accountNo")
	@Mapping(target = "fullName", source = "individualRequest.fullNameRequest")
	//dtos to entity
	Individual toEntity(IndividualRequest individualRequest);
	//entity to dtos
	IndividualResponse toRequest(Individual individual);
	//entity list to dto list
	List<IndividualResponse> toResponseList(List<Individual> individuals);

}
