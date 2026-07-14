package com.cognizant.product.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cognizant.product.dtos.CatalogRequest;
import com.cognizant.product.dtos.CatalogResponse;
import com.cognizant.product.entities.Catalog;

@Mapper(componentModel = "spring")
public interface CatalogMapper {	
	//dtos to entities
	Catalog toCatalog(CatalogRequest catalogRequest);
	
	//entity to dtos
	CatalogResponse toCatalogResponse(Catalog catalog);
	
	List<CatalogResponse> toCatalogResponseList(List<Catalog> catalogs);
}
