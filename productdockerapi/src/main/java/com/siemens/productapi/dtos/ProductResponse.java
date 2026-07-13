package com.siemens.productapi.dtos;

import java.time.LocalDate;

public record ProductResponse(long productId, String productName, double price, LocalDate dop) {
}
