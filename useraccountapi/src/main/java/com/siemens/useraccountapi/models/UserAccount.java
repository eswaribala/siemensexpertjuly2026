package com.siemens.useraccountapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="User_Account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccount implements Serializable {

    @Id
    @GeneratedValue(generator = "user-id")
    @GenericGenerator(name="user-id",strategy = "com.siemens.useraccountapi.models.CustomUserIdGenerator")
    @Column(name="User_Id")
    private String userId;
    @Embedded
    private FullName fullName;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name="Email",nullable = false,length = 150)

    private String email;

    @Column(name="Password",nullable = false,length = 10)
    private String password;

    @Column(name="DOB")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dob;



}
