package com.cognizant.customerservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.customerservice.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
