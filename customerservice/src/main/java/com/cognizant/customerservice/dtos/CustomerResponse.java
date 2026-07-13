package com.cognizant.customerservice.dtos;

public record CustomerResponse(Long accountNo, FullNameResponse fullName, String email, long contactNo) {

}
