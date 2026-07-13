package com.cognizant.customerservice.dtos;

import lombok.Data;

@Data
public class GenericMessage<T> {
	private T data;
	private String message;
	public GenericMessage(T data) {
		super();
		this.data = data;
	}
	public GenericMessage(String message) {
		super();
		this.message = message;
	}
	

}
