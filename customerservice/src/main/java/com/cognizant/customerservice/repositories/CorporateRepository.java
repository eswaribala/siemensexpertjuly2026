package com.cognizant.customerservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.customerservice.entities.Corporate;

public interface CorporateRepository extends JpaRepository<Corporate, Long> {

}
