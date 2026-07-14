package com.cognizant.product.exceptions;

import java.util.stream.Collectors;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cognizant.product.dtos.GenericResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<GenericResponse<String>> handleProductNotFoundException(ProductNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
				.body(new GenericResponse<>(ex.getMessage()));
	}
	
	@ExceptionHandler(CatalogNotFoundException.class)
	public ResponseEntity<GenericResponse<String>> handleCatalogNotFoundException(CatalogNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
				.body(new GenericResponse<>(ex.getMessage()));
	}
	
	

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<GenericResponse<String>> handleRuntimeException(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
				.body(new GenericResponse<>("An unexpected error occurred: " + ex.getMessage()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<GenericResponse<String>> handleValidationException(MethodArgumentNotValidException ex) {
		String errorMessage = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.joining(", "));
		return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
				.body(new GenericResponse<>(errorMessage));
	}
	
	
}
