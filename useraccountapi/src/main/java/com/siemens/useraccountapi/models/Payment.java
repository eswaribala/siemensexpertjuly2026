package com.siemens.useraccountapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name="Payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Transaction_Id")
    private long transactionId;
    @Column(name="Buyer_Name",nullable = false,length = 100)
    private String buyerName;
    @Column(name="Amount")
    private long amount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="DateOfTransaction")
    private LocalDate dot;
}
