package com.example.cqrs.query.repository;

import com.example.cqrs.command.model.OrderStatus;
import com.example.cqrs.query.model.OrderView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderQueryRepository extends JpaRepository<OrderView, UUID> {
    List<OrderView> findByStatusOrderByCreatedAtDesc(OrderStatus status);
}
