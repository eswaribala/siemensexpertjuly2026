package com.cognizant.customerservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.customerservice.dtos.GenericMessage;
import com.cognizant.customerservice.dtos.IndividualRequest;
import com.cognizant.customerservice.dtos.IndividualResponse;
import com.cognizant.customerservice.dtos.UpdateRequest;
import com.cognizant.customerservice.entities.Individual;
import com.cognizant.customerservice.mappers.IndividualMapper;
import com.cognizant.customerservice.services.IndividualService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/individuals")
public class IndividualController {
	@Autowired
	private IndividualService individualService;
	@Autowired
	private IndividualMapper individualMapper;
	//validation can be added using @Valid and validation annotations in the DTO
	@PostMapping("/v1.0")
	@PreAuthorize("hasAnyAuthority('SCOPE_sre')")
	public ResponseEntity<GenericMessage<IndividualResponse>> createIndividual(@Valid @RequestBody IndividualRequest individualRequest) {
		//dto to entity
		Individual individual = individualMapper.toEntity(individualRequest);
		Individual savedIndividual = individualService.addIndividual(individual);
		//entity to dto
		IndividualResponse individualResponse = 
				individualMapper.toRequest(savedIndividual);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new GenericMessage<IndividualResponse>(individualResponse));
	}
	@GetMapping("/v1.0")
	@PreAuthorize("hasAnyAuthority('SCOPE_sre','SCOPE_devopsengineer')")
	public ResponseEntity<GenericMessage<List<IndividualResponse>>> getAllIndividuals() {
		List<Individual> individuals = individualService.getAllIndividuals();
		List<IndividualResponse> individualResponses = 
				individualMapper.toResponseList(individuals);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new GenericMessage<List<IndividualResponse>>(individualResponses));
		
	}
	
	@GetMapping("/pages/v1.0")
	@PreAuthorize("hasAnyAuthority('SCOPE_devopsengineer')")
	public ResponseEntity<GenericMessage<List<IndividualResponse>>> getAllIndividualsByPage(
			@RequestParam int page, @RequestParam int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<Individual> individuals = individualService.getAllIndividualsByPagination(pageable);
		List<IndividualResponse> individualResponses = 
				individualMapper.toResponseList(individuals);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new GenericMessage<List<IndividualResponse>>(individualResponses));
		
	}
	
	@GetMapping("/id/v1.0/{id}")
	public ResponseEntity<GenericMessage<IndividualResponse>> getIndividualsById
	(@PathVariable Long id) {
		
	    Individual individual = individualService.getIndividualById(id);
		IndividualResponse individualResponse = 
				individualMapper.toRequest(individual);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new GenericMessage<IndividualResponse>(individualResponse));
		
	}
	@GetMapping("/contactNo/v1.0")
	public ResponseEntity<GenericMessage<List<IndividualResponse>>> getIndividualsByContactNo
	(@RequestParam long contactNo) {
		
	    List<Individual> individuals = individualService.getIndividualsByContactNo(contactNo);
		
		List<IndividualResponse> individualResponses = 
				individualMapper.toResponseList(individuals);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new GenericMessage<List<IndividualResponse>>(individualResponses));
		
	}
	
	
	@PatchMapping("/v1.0/{id}")
	@PreAuthorize("hasAnyAuthority('SCOPE_sre')")
	public ResponseEntity<GenericMessage<IndividualResponse>> updateIndividualById
	(@PathVariable Long id, @Valid @RequestBody UpdateRequest updateRequest) {
		
	    Individual updatedIndividual = individualService.updateIndividual(id, 
	    		updateRequest.getEmail(),updateRequest.getContactNo());
		IndividualResponse individualResponse = individualMapper.toRequest(updatedIndividual);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new GenericMessage<IndividualResponse>(individualResponse));
		
	}
	
	@DeleteMapping("/v1.0/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_sre')")
	public ResponseEntity<GenericMessage<String>> deleteIndividualById
	(@PathVariable Long id) {
		
	    if(individualService.deleteIndividual(id))
		
		   return ResponseEntity.status(HttpStatus.OK)
				.body(new GenericMessage<>("Individual with id "+id+" deleted successfully"));
	    else
	    	return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new GenericMessage<>("Individual with id "+id+" not found"));
		
	} 
	
	
	

}
