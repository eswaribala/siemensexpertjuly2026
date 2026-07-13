package com.siemens.useraccountapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name="CardPayment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class CardPayment extends Payment {
    @Column(name="Card_No")
    private long cardNo;
    @Enumerated(EnumType.STRING)
    @Column(name="Card_Type")
    private CardType cardType;
    @Column(name="CVV_No")
    private int cvvNo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="Expiry_Date")
    private LocalDate expiryDate;


}
