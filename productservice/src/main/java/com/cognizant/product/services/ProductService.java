package com.cognizant.product.services;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.support.SendResult;

import com.cognizant.product.dtos.ProductCatalogResponse;
import com.cognizant.product.entities.Product;
import com.cognizant.product.exceptions.CatalogNotFoundException;
import com.cognizant.product.exceptions.ProductNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProductService {	
	Product addProduct(long catalogId,Product product) throws CatalogNotFoundException;
	List<Product> getAllProducts();
	Product getProductById(long productId) throws ProductNotFoundException;
	Product updateProduct(long productId, long price) throws ProductNotFoundException;
	boolean deleteProduct(long productId) throws ProductNotFoundException;
    List<ProductCatalogResponse> getProductsAndCatalogs();    
    CompletableFuture<SendResult<String, String>> sendProductToKafka(long productId) 
    		throws ProductNotFoundException,JsonProcessingException;
}
