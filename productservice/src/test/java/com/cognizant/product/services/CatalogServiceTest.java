package com.cognizant.product.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cognizant.product.entities.Catalog;
import com.cognizant.product.repositories.CatalogRepository;
import com.github.javafaker.Faker;
import org.mockito.Mockito;

@ExtendWith(MockitoExtension.class)
public class CatalogServiceTest {
    @Mock
	private CatalogRepository catalogRepository;
    @InjectMocks
    private CatalogServiceImpl catalogService;
    @Test
    public void addCatalogMockTest() {
    	//create a catalog object with random data using Faker
    	Catalog catalog = getCatalogList()
    			.get(new Faker().number().numberBetween(0, 9));
    	Mockito.when(catalogRepository.save(catalog)).thenReturn(catalog);
    	Catalog savedCatalog = catalogService.addCatalog(catalog);
    	assertEquals(savedCatalog.getCatalogId(), catalog.getCatalogId());  
    
    }
    @Test
    public void getAllCatalogsMockTest() {
		List<Catalog> catalogList = getCatalogList();
		Mockito.when(catalogRepository.findAll()).thenReturn(catalogList);
		List<Catalog> retrievedCatalogList = catalogService.getAllCatalogs();
		assertEquals(retrievedCatalogList.size(), catalogList.size());
	}
    @Test
    public void getCatalogByIdMockTest() throws Exception {
		Catalog catalog = getCatalogList()
				.get(new Faker().number().numberBetween(0, 9));
		Mockito.when(catalogRepository.findById(catalog.getCatalogId()))
				.thenReturn(Optional.of(catalog));
		Catalog retrievedCatalog = catalogService.getCatalogById(catalog.getCatalogId());
		assertEquals(retrievedCatalog.getCatalogId(), catalog.getCatalogId());
	}
    @Test
    public void updateCatalogMockTest() throws Exception {
    	String newCatalogName = new Faker().commerce().productName();
    	Catalog catalog = getCatalogList()
				.get(new Faker().number().numberBetween(0, 9));
    	catalog.setCatalogName(newCatalogName);
		Mockito.when(catalogRepository.findById(catalog.getCatalogId()))
				.thenReturn(Optional.of(catalog));
		Mockito.when(catalogRepository.save(catalog)).thenReturn(catalog);
		Catalog updatedCatalog = catalogService.updateCatalog(catalog);
		assertEquals(updatedCatalog.getCatalogName(), newCatalogName);
    }
    @Test
    public void deleteCatalogMockTest() throws Exception {
    	Catalog catalog = getCatalogList()
				.get(new Faker().number().numberBetween(0, 9));
		Mockito.when(catalogRepository.existsById(catalog.getCatalogId()))
						.thenReturn(true);
		Mockito.doNothing().when(catalogRepository)
		      .deleteById(catalog.getCatalogId());
		boolean isDeleted = catalogService.deleteCatalog(catalog.getCatalogId());
		assertEquals(isDeleted, true);
    	
    }
    
    private List<Catalog> getCatalogList() {
    	List<Catalog> catalogList = new ArrayList<>();
    	Faker faker = new Faker();
    	for(int i=0;i<10;i++) {
			Catalog catalog = new Catalog();
			catalog.setCatalogId(faker.number().numberBetween(1, 100));
			catalog.setCatalogName(faker.commerce().productName());
			catalogList.add(catalog);		}
		return catalogList;		    	
    }
    
}
