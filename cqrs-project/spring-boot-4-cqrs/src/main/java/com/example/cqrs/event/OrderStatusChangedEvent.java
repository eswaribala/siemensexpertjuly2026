package com.example.cqrs.event;

import com.example.cqrs.command.model.OrderStatus;

import java.time.Instant;
import java.util.UUID;

public record OrderStatusChangedEvent(
        UUID eventId,
        UUID orderId,
        OrderStatus status,
        Instant occurredAt
) {
}