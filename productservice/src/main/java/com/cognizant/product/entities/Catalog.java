package com.cognizant.product.entities;

import com.cognizant.product.facades.CatalogIdAnnotator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "catalog")
public class Catalog {
	@Id
	@CatalogIdAnnotator
    @Column(name = "catalog_id") 
	private long catalogId;
    @Column(name = "catalog_name", nullable = false,length = 100, unique = true)
	private String catalogName;
}
