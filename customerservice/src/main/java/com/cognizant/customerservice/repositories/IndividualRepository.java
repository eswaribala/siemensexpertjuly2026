package com.cognizant.customerservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.customerservice.entities.Individual;

public interface IndividualRepository extends JpaRepository<Individual, Long> {

	List<Individual> findByContactNo(long contactNo);
}
