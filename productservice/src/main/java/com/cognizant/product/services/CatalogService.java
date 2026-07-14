package com.cognizant.product.services;

import java.util.List;

import com.cognizant.product.entities.Catalog;
import com.cognizant.product.exceptions.CatalogNotFoundException;

public interface CatalogService {
	
	Catalog addCatalog(Catalog catalog);
	List<Catalog> getAllCatalogs();
	Catalog getCatalogById(long catalogId) throws CatalogNotFoundException;
	Catalog updateCatalog(Catalog catalog) throws CatalogNotFoundException;
	boolean deleteCatalog(long catalogId) throws CatalogNotFoundException;

}
