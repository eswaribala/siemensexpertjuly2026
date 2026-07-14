package com.cognizant.product.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cognizant.product.dtos.ProductRequest;
import com.cognizant.product.dtos.ProductResponse;
import com.cognizant.product.entities.Product;
import com.cognizant.product.mappers.ProductMapper;
import com.cognizant.product.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

@WebMvcTest(ProductController.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.cloud.vault.enabled=false",
        "spring.config.import=",
        "auditServiceUrl=http://localhost:9999/audit"
})
public class ProductControllerTest {
	@MockitoBean
	private ProductService productService;
	@MockitoBean
	private ProductMapper productMapper;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Test
	public void addProductTest() throws JsonProcessingException, Exception {
		
		 Product product = getSampleProducts().get(0);
		 System.out.println(product);
		 ProductRequest productRequest = new ProductRequest();
		 productRequest.setProductName(product.getProductName());
		 productRequest.setDescription(product.getDescription());
		 productRequest.setPrice(product.getPrice());
		 productRequest.setPurchaseDate(product.getPurchaseDate());
		 productRequest.setExpiryDate(product.getExpiryDate());
		 
		 ProductResponse productResponse = productMapper.toProductResponse(product);		 

		 Mockito.when(productMapper.toProduct(Mockito.any(ProductRequest.class)))
         .thenReturn(product);
		 Mockito.when(productService.addProduct(Mockito.eq(0L), Mockito.any(Product.class)))
			.thenReturn(product);

		 Mockito.when(productMapper.toProductResponse(Mockito.any(Product.class)))
			.thenReturn(productResponse);

			mockMvc.perform(post("/products/v1.0").param("catalogId", "0")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(productRequest))
					.with(jwt().authorities(() -> "SCOPE_sre")))
			         .andDo(print()).andExpect(status().isCreated());
					
		
	}
	
	@Test
	public void getAllProducts() throws Exception {
		List<Product> products = getSampleProducts();
		List<ProductResponse> responses = new ArrayList<>();
	    for (int i = 0; i < products.size(); i++) 
	    	responses.add(Mockito.mock(ProductResponse.class)); // safe

		//pre configure the mock behavior for productService.getAllProducts()
		Mockito.when(productService.getAllProducts())
			.thenReturn(products);
		Mockito.when(productMapper.toProductResponseList(Mockito.anyList()))
		.thenReturn(responses);
		mockMvc.perform(get("/products/v1.0")
				.with(jwt().authorities(() -> "SCOPE_sre")))
			.andDo(print())			
			.andExpect(status().isOk())	
			.andExpect(jsonPath("$.data", Matchers.hasSize(products.size())))
	        .andDo(print());					
		
	}
	@Test
	public void getProductById() throws Exception {
		Product product=getSampleProducts().get(0);
		Mockito.when(productService.getProductById(Mockito.anyLong()))
		    .thenReturn(product);
		ProductResponse response = Mockito.mock(ProductResponse.class);
		Mockito.when(productMapper.toProductResponse(Mockito.any(Product.class)))
		    .thenReturn(response);
		mockMvc.perform(get("/products/v1.0/productById")
				.param("productId", "0")
				.with(jwt().authorities(() -> "SCOPE_sre")))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.data").exists())
		  		.andDo(print());
		
	}
	@Test
	public void updateProduct() throws Exception {
		Product product=getSampleProducts().get(0);
		Mockito.when(productService.updateProduct(Mockito.anyLong(), Mockito.anyLong()))
		    .thenReturn(product);
		ProductResponse response = Mockito.mock(ProductResponse.class);
		Mockito.when(productMapper.toProductResponse(Mockito.any(Product.class)))
		    .thenReturn(response);
		mockMvc.perform(patch("/products/v1.0")
				.param("productId", "0")
				.param("price", "1000")
				.with(jwt().authorities(() -> "SCOPE_sre")))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.data").exists())
		  		.andDo(print());		
	}
	@Test
	public void deleteProduct() throws Exception {
		Mockito.when(productService.deleteProduct(Mockito.anyLong()))
		    .thenReturn(true);
		mockMvc.perform(delete("/products/v1.0")
				.param("productId", "0")
				.with(jwt().authorities(() -> "SCOPE_sre")))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.data").value("Product with id 0 deleted successfully"))
		  		.andDo(print());		
	}
	
	
	private List<Product> getSampleProducts() {
		List<Product> products = new ArrayList<>();
	    Faker faker = new Faker();

	    for (int i = 0; i < 5; i++) {
	        Product product = new Product();
	        product.setProductId(faker.number().randomNumber());
	        product.setProductName(faker.commerce().productName().replaceAll("\\s+", ""));
	        product.setDescription(faker.lorem().sentence());
	        product.setPrice(Double.parseDouble(faker.commerce().price()));
	        product.setPurchaseDate(java.time.LocalDate.now());
	        product.setExpiryDate(java.time.LocalDate.now().plusYears(1));
	        products.add(product);
	    }
	    return products;
	}
	

}
