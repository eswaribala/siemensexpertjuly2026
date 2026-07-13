package com.cognizant.customerservice.entities;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "individual")
public class Individual extends Customer{
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
	private Gender gender;
    @Column(name = "date_of_birth")
    @DateTimeFormat(iso=ISO.DATE)
    private LocalDate dateOfBirth;
}
