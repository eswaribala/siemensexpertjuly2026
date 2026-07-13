package com.cognizant.customerservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.customerservice.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
