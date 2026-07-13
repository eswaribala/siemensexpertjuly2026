package com.cognizant.customerservice.exceptions;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cognizant.customerservice.dtos.GenericMessage;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<GenericMessage<String>> handleCustomerNotFoundException
	(CustomerNotFoundException ex) {
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new GenericMessage<>(ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<GenericMessage<String>> handleMethodArgumentNotValidException
	
	(MethodArgumentNotValidException ex) {
	    String errorMessage = ex.getBindingResult().getAllErrors().stream()
	            .map(error -> error.getDefaultMessage())
	            .collect(Collectors.joining(", "));
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	            .body(new GenericMessage<>(errorMessage));
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<GenericMessage<String>> handleRuntimeException
	(RuntimeException ex) {
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new GenericMessage<>(ex.getMessage()));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<GenericMessage<String>> handleException
	(Exception ex) {
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new GenericMessage<>(ex.getMessage()));
	}
	
}
