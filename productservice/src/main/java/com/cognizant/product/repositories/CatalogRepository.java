package com.cognizant.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.product.entities.Catalog;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
	
	

}
