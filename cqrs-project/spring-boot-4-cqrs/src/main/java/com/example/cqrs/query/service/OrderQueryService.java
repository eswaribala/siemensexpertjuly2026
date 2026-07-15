package com.example.cqrs.query.service;

import com.example.cqrs.command.model.OrderStatus;
import com.example.cqrs.query.model.OrderView;
import com.example.cqrs.query.repository.OrderQueryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class OrderQueryService {

    private final OrderQueryRepository repository;

    public OrderQueryService(OrderQueryRepository repository) {
        this.repository = repository;
    }

    public OrderView findById(UUID orderId) {
        return repository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order view not available yet: " + orderId
                ));
    }

    public List<OrderView> findAll() {
        return repository.findAll();
    }

    public List<OrderView> findByStatus(OrderStatus status) {
        return repository.findByStatusOrderByCreatedAtDesc(status);
    }
}
