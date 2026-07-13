package com.cognizant.customerservice.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.cognizant.customerservice.entities.Individual;

public interface IndividualService {
	
	Individual addIndividual(Individual individual);
	Individual getIndividualById(Long id);
	List<Individual> getAllIndividuals();
	Individual updateIndividual(Long id, String email, long phoneNumber);
	boolean deleteIndividual(Long id);
	List<Individual> getAllIndividualsByPagination(Pageable pageable);
	List<Individual> getIndividualsByContactNo(long contactNo);

}
