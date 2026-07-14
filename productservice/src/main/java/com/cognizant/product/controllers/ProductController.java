package com.cognizant.product.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.product.dtos.GenericResponse;
import com.cognizant.product.dtos.ProductCatalogResponse;
import com.cognizant.product.dtos.ProductRequest;
import com.cognizant.product.dtos.ProductResponse;
import com.cognizant.product.entities.Product;
import com.cognizant.product.exceptions.CatalogNotFoundException;
import com.cognizant.product.exceptions.ProductNotFoundException;
import com.cognizant.product.mappers.ProductMapper;
import com.cognizant.product.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@CrossOrigin(
	    origins = {
	        "http://localhost:8765",
	        "http://localhost:7074",
	        "http://localhost:7076"
	    },
	    allowCredentials = "true"
	)
public class ProductController {
    @Autowired
	private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    
    @PostMapping("/v1.0")
    @PreAuthorize("hasAnyAuthority('SCOPE_sre')")
    public ResponseEntity<GenericResponse<ProductResponse>> addProduct(@Valid @RequestBody ProductRequest productRequest,@RequestParam long catalogId) throws CatalogNotFoundException {
		Product product = productMapper.toProduct(productRequest);
		Product savedProduct = productService.addProduct(catalogId, product);
		ProductResponse productResponse = productMapper.toProductResponse(savedProduct);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new GenericResponse<ProductResponse>(productResponse));
	}
    @GetMapping("/v1.0")
    @PreAuthorize("hasAnyAuthority('SCOPE_sre','SCOPE_devopsengineer')")
    public ResponseEntity<GenericResponse<List<ProductResponse>>> getAllProducts() {
    	List<Product> products = productService.getAllProducts();
    	List<ProductResponse> productResponses = productMapper.toProductResponseList(products);
    			return ResponseEntity.status(HttpStatus.OK)
               .body(new GenericResponse<List<ProductResponse>>(productResponses));
    }
    @GetMapping("/v1.0/productById")
    public ResponseEntity<GenericResponse<ProductResponse>> getProductById(@RequestParam long productId) throws ProductNotFoundException {
		Product product = productService.getProductById(productId);
		ProductResponse productResponse = productMapper.toProductResponse(product);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new GenericResponse<ProductResponse>(productResponse));
	}
    
    @PatchMapping("/v1.0")    
    public ResponseEntity<GenericResponse<ProductResponse>> updateProduct(@RequestParam long productId, @RequestParam long price) throws ProductNotFoundException {
		
		Product updatedProduct = productService.updateProduct(productId, price);
		ProductResponse productResponse = productMapper.toProductResponse(updatedProduct);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new GenericResponse<ProductResponse>(productResponse));
	}
    
    @DeleteMapping("/v1.0")
    public ResponseEntity<GenericResponse<String>> deleteProduct(@RequestParam long productId) throws ProductNotFoundException {
    	if(productService.deleteProduct(productId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new GenericResponse<String>("Product with id "+productId+" deleted successfully"));
		}
		else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new GenericResponse<String>("Failed to delete product with id "+productId));
		}
    }
    
    @GetMapping("/v1.0/productsAndCatalogs")
    public ResponseEntity<GenericResponse<List<ProductCatalogResponse>>> getProductsWithCatalogs() {
    	List<ProductCatalogResponse> productsAndCatalogs= productService.getProductsAndCatalogs();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new GenericResponse<List<ProductCatalogResponse>>(productsAndCatalogs));
	}
    
    @GetMapping("/v1.0/sendToKafka")
    public CompletableFuture<ResponseEntity<GenericResponse<String>>> 
    sendProductToKafka(@RequestParam long productId) throws ProductNotFoundException,
    JsonProcessingException {
		return productService.sendProductToKafka(productId)
				.thenApply(sendResult -> ResponseEntity.status(HttpStatus.OK)
						.body(new GenericResponse<String>("Product with id "+productId+" "
								+ "sent to Kafka topic successfully"
								+sendResult.getProducerRecord().value()+
								" with offset "
								+sendResult.getRecordMetadata().offset()+" and partition "
								+sendResult.getRecordMetadata().partition())))
				.exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new GenericResponse<String>("Failed to send product with id "+
				productId+" to Kafka topic: " + ex.getMessage())));
	}
    
    
    
}
