package com.cognizant.product.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private long productId;
	@Column(name = "product_name", nullable = false, length = 100)
	private String productName;
	@Column(name = "description", length = 500)
	private String description;
	@Column(name = "price")
	private double price;
	@Column(name = "purchase_date")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate purchaseDate;
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "expiry_date")
	private LocalDate expiryDate;
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(foreignKey = @ForeignKey(name = "catalogId"),name = "catalog_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Catalog catalog;

}
