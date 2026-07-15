package com.example.cqrs.command.service;

import com.example.cqrs.command.api.CreateOrderCommand;
import com.example.cqrs.command.model.Order;
import com.example.cqrs.command.repository.OrderCommandRepository;
import com.example.cqrs.event.OrderCreatedEvent;
import com.example.cqrs.event.OrderStatusChangedEvent;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderCommandService {

    private final OrderCommandRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public OrderCommandService(OrderCommandRepository repository,
                               ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public UUID createOrder(CreateOrderCommand command) {
        Order order = new Order(
                UUID.randomUUID(),
                command.customerName(),
                command.productName(),
                command.quantity(),
                command.unitPrice()
        );

        repository.save(order);

        eventPublisher.publishEvent(new OrderCreatedEvent(
                UUID.randomUUID(),
                order.getId(),
                order.getCustomerName(),
                order.getProductName(),
                order.getQuantity(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                java.time.Instant.now()
        ));

        return order.getId();
    }

    @Transactional
    public void confirmOrder(UUID orderId) {
        Order order = findOrder(orderId);
        order.confirm();
        eventPublisher.publishEvent(
                new OrderStatusChangedEvent(
                        UUID.randomUUID(),
                        order.getId(),
                        order.getStatus(),
                        java.time.Instant.now()
                )
        );
    }

    @Transactional
    public void cancelOrder(UUID orderId) {
        Order order = findOrder(orderId);
        order.cancel();
        eventPublisher.publishEvent(
                new OrderStatusChangedEvent(
                        UUID.randomUUID(),
                        order.getId(),
                        order.getStatus(),
                        java.time.Instant.now()
                )
        );
    }

    private Order findOrder(UUID orderId) {
        return repository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order not found: " + orderId
                ));
    }
}
