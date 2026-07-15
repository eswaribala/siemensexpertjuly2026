package com.example.cqrs.query.model;

import com.example.cqrs.command.model.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders_read")
public class OrderView {

    @Id
    private UUID orderId;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String orderDescription;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private Instant createdAt;

    protected OrderView() {
    }

    public OrderView(UUID orderId, String customerName, String orderDescription,
                     Integer quantity, BigDecimal totalAmount,
                     OrderStatus status, Instant createdAt) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDescription = orderDescription;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void changeStatus(OrderStatus status) {
        this.status = status;
    }

    public UUID getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public String getOrderDescription() { return orderDescription; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
