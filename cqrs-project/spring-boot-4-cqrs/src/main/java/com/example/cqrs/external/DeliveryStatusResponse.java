package com.example.cqrs.external;

public record DeliveryStatusResponse(
        String referenceId,
        String status,
        String message
) {
}