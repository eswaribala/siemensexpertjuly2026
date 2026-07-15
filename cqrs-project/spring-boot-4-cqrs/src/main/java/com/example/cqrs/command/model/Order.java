package com.example.cqrs.command.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders_write")
public class Order {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private Instant createdAt;

    @Version
    private Long version;

    protected Order() {
    }

    public Order(UUID id, String customerName, String productName,
                 Integer quantity, BigDecimal unitPrice) {
        this.id = id;
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = unitPrice.multiply(BigDecimal.valueOf(quantity));
        this.status = OrderStatus.CREATED;
        this.createdAt = Instant.now();
    }

    public void confirm() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Only CREATED orders can be confirmed");
        }
        status = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already cancelled");
        }
        status = OrderStatus.CANCELLED;
    }

    public UUID getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getProductName() { return productName; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
