package com.siemens.productapi.mappers;

import com.siemens.productapi.dtos.ProductRequest;
import com.siemens.productapi.dtos.ProductResponse;
import com.siemens.productapi.models.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    //dto to entity
    Product toEntity(ProductRequest productRequest);

    //entity to dto

    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponseList(List<Product> products);


}
