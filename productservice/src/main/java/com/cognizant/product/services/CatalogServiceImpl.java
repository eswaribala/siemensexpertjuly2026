package com.cognizant.product.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cognizant.product.entities.Catalog;
import com.cognizant.product.exceptions.CatalogNotFoundException;
import com.cognizant.product.repositories.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class CatalogServiceImpl implements CatalogService {
	@Autowired
	private CatalogRepository catalogRepository;

	@Override
	public Catalog addCatalog(Catalog catalog) {
		// TODO Auto-generated method stub
		return catalogRepository.save(catalog);
	}

	@Override
	public List<Catalog> getAllCatalogs() {
		// TODO Auto-generated method stub
		return catalogRepository.findAll();
	}

	@Override
	public Catalog getCatalogById(long catalogId) throws CatalogNotFoundException {
		// TODO Auto-generated method stub
		return catalogRepository.findById(catalogId)
				.orElseThrow(() -> new CatalogNotFoundException("Catalog not found with id: " + catalogId));
	}

	@Override
	public Catalog updateCatalog(Catalog catalog) throws CatalogNotFoundException {
		// TODO Auto-generated method stub
		Catalog existingCatalog = catalogRepository.findById(catalog.getCatalogId())
				.orElseThrow(() -> new CatalogNotFoundException("Catalog not found with id: " + catalog.getCatalogId()));
          existingCatalog.setCatalogName(catalog.getCatalogName());         
          return catalogRepository.save(existingCatalog);     
       
	
	}

	@Override
	public boolean deleteCatalog(long catalogId) throws CatalogNotFoundException {
		// TODO Auto-generated method stub
		if(catalogRepository.existsById(catalogId))
			{
			catalogRepository.deleteById(catalogId);
			return true;
		}
		else
		{
			throw new CatalogNotFoundException("Catalog not found with id: " + catalogId);
		}
	}



}
