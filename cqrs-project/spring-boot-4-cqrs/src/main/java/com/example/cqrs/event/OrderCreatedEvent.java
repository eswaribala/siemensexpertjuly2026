package com.example.cqrs.event;

import com.example.cqrs.command.model.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID eventId,
        UUID orderId,
        String customerName,
        String productName,
        Integer quantity,
        BigDecimal totalAmount,
        OrderStatus status,
        Instant createdAt,
        Instant occurredAt
) {
}