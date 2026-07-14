package com.cognizant.product.repositories;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cognizant.product.dtos.ProductCatalogResponse;
import com.cognizant.product.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("SELECT c.catalogName, p.productName FROM Catalog c, Product p WHERE c.catalogId = p.catalog.catalogId")
	List<Object[]> findProductAndCatalog();

}
