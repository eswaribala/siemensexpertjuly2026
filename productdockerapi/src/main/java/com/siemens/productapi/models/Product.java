package com.siemens.productapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long productId;
    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;
    @Column(name = "dop")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dop;
    @Column(name="price")
    private double price;

}
