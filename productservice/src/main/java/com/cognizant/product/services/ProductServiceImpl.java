package com.cognizant.product.services;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.cognizant.product.dtos.ProductCatalogResponse;
import com.cognizant.product.entities.Product;
import com.cognizant.product.exceptions.CatalogNotFoundException;
import com.cognizant.product.exceptions.ProductNotFoundException;
import com.cognizant.product.repositories.CatalogRepository;
import com.cognizant.product.repositories.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CatalogRepository catalogRepository;
	@Value("${topicName}")
	private String topicName;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Override
	public Product addProduct(long catalogId, Product product) throws CatalogNotFoundException {
		// TODO Auto-generated method stub
		if(catalogRepository.existsById(catalogId)) {
			product.setCatalog(catalogRepository.findById(catalogId).get());
			return productRepository.save(product);
		}else {
			throw new CatalogNotFoundException("Catalog with id " + catalogId + " not found.");
		}
		
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(long productId) throws ProductNotFoundException {
		// TODO Auto-generated method stub
		return productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found."));
	}

	@Override
	public Product updateProduct(long productId, long price) throws ProductNotFoundException {
		// TODO Auto-generated method stub
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found."));
		product.setPrice(price);
		return productRepository.save(product);
	}

	@Override
	public boolean deleteProduct(long productId) throws ProductNotFoundException {
		// TODO Auto-generated method stub
		if(productRepository.existsById(productId)) {
			productRepository.deleteById(productId);
	  			return true;
		}else {
			throw new ProductNotFoundException("Product with id " + productId + " not found.");
		}
	}

	@Override
	public List<ProductCatalogResponse> getProductsAndCatalogs() {
		// TODO Auto-generated method stub
		List<Object[]> objects=productRepository.findProductAndCatalog();
		return objects.stream()
				.map(obj -> new ProductCatalogResponse((String) obj[0], (String) obj[1]))
				.toList();
	}

	@Override
	public CompletableFuture<SendResult<String, String>> sendProductToKafka(long productId)
			throws ProductNotFoundException, JsonProcessingException {
		// TODO Auto-generated method stub
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found."));
		//java object to json string
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
		String productJson=objectWriter.writeValueAsString(product);
		return kafkaTemplate.send(topicName, productJson);				
	}

}
