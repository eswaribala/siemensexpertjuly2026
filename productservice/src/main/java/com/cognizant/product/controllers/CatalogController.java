package com.cognizant.product.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.cognizant.product.dtos.AuditMessage;
import com.cognizant.product.dtos.CatalogRequest;
import com.cognizant.product.dtos.CatalogResponse;
import com.cognizant.product.dtos.GenericResponse;
import com.cognizant.product.entities.Catalog;
import com.cognizant.product.exceptions.CatalogNotFoundException;
import com.cognizant.product.mappers.CatalogMapper;
import com.cognizant.product.services.CatalogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/catalogs")
@CrossOrigin(
	    origins = {
	        "http://localhost:8765",
	        "http://localhost:7074",
	        "http://localhost:7076",
	        "http://localhost:8084",
	    },
	    allowCredentials = "true"
	)
public class CatalogController {
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private CatalogMapper catalogmapper;
	@Autowired
	private RestClient restClient;
	@Value("${auditServiceUrl}")
	private String auditServiceUrl;
	private AuditMessage auditMessage;
	private Authentication authentication;
	
	
	@PostMapping("/v1.0")
	@PreAuthorize("hasAnyAuthority('SCOPE_sre')")
	public ResponseEntity<GenericResponse<CatalogResponse>> addCatalog(@Valid @RequestBody CatalogRequest request) {
		auditMessage = new AuditMessage();
		authentication=SecurityContextHolder.getContext()
				.getAuthentication();
		auditMessage.setUserName(authentication.getName());
		Jwt jwt = (Jwt) authentication.getPrincipal();
		auditMessage.setRole(jwt.getClaimAsString("scope"));
		auditMessage.setAction(this.getClass().getSimpleName()+"-"+Thread.currentThread().getStackTrace()[1].getMethodName());
		auditMessage.setTimestamp(java.time.LocalDateTime.now());
		restClient.post().uri(auditServiceUrl).body(auditMessage).retrieve()
        .toBodilessEntity();
		Catalog catalog = catalogmapper.toCatalog(request);
		Catalog response=catalogService.addCatalog(catalog);
		CatalogResponse catalogResponse = catalogmapper.toCatalogResponse(response);
		return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<CatalogResponse>(
			catalogResponse	));	
	}
	
	@GetMapping("/v1.0")
	@PreAuthorize("hasAnyAuthority('SCOPE_sre','SCOPE_devopsengineer')")
	public ResponseEntity<GenericResponse<List<CatalogResponse>>> getAllCatalogs() {
		auditMessage = new AuditMessage();
		authentication=SecurityContextHolder.getContext()
				.getAuthentication();
		auditMessage.setUserName(authentication.getName());
		Jwt jwt = (Jwt) authentication.getPrincipal();
		auditMessage.setRole(jwt.getClaimAsString("scope"));
		auditMessage.setAction(this.getClass().getSimpleName()+"-"+Thread.currentThread().getStackTrace()[1].getMethodName());
		auditMessage.setTimestamp(java.time.LocalDateTime.now());
		restClient.post().uri(auditServiceUrl).body(auditMessage).retrieve()
        .toBodilessEntity();
		List<Catalog> catalogs = catalogService.getAllCatalogs();
	    List<CatalogResponse> catalogResponses = catalogmapper.
	    		toCatalogResponseList(catalogs);
	    return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse
	    		<List<CatalogResponse>>(catalogResponses));
	}
	
	@GetMapping("/v1.0/{id}")
	public ResponseEntity<GenericResponse<CatalogResponse>> getCatalogById(@PathVariable Long id) throws CatalogNotFoundException {
		Catalog catalog = catalogService.getCatalogById(id);
	    CatalogResponse catalogResponse = catalogmapper.toCatalogResponse(catalog);
	    return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<CatalogResponse>(catalogResponse));
	}
	@PutMapping("/v1.0")
	public ResponseEntity<GenericResponse<CatalogResponse>> updateCatalog(@RequestParam long catalogId, @RequestParam String catalogName) throws CatalogNotFoundException {
		
		Catalog catalog = new Catalog();
		catalog.setCatalogId(catalogId);
		catalog.setCatalogName(catalogName);
		Catalog updatedCatalog = catalogService.updateCatalog(catalog);
	    CatalogResponse catalogResponse = catalogmapper.toCatalogResponse(updatedCatalog);
	    return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<CatalogResponse>(catalogResponse));
	}
	
	@DeleteMapping("/v1.0/{id}")
	public ResponseEntity<GenericResponse<String>> deleteCatalogById(@PathVariable Long id) throws CatalogNotFoundException {
		if(catalogService.deleteCatalog(id))
	      return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<String>("Catalog deleted successfully"));
		else
		  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse<String>("Catalog not found"));
	}

}
