package com.example.cqrs.command.repository;

import com.example.cqrs.command.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderCommandRepository extends JpaRepository<Order, UUID> {
}
