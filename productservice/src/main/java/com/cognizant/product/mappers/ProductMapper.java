package com.cognizant.product.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cognizant.product.dtos.ProductRequest;
import com.cognizant.product.dtos.ProductResponse;
import com.cognizant.product.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
	
	//dtos to entities
	Product toProduct(ProductRequest productRequest);
	//entity to dtos
	ProductResponse toProductResponse(Product product);
	List<ProductResponse> toProductResponseList(List<Product> products);

}
