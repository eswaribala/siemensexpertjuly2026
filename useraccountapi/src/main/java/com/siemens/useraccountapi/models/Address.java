package com.siemens.useraccountapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Address_Id")
    private long addressId;
    @Column(name="Door_No", nullable = false,length = 5)
    private String doorNo;
    @Column(name="Street_Name", nullable = false,length = 100)
    private String streetName;
    @Column(name="City", nullable = false,length = 100)
    private String city;
    @Column(name="Country", nullable = false,length = 100)
    private String country;
    @Column(name="Pincode")
    private long pinCode;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "User_Id"),
            name = "User_Id_FK")
    private UserAccount userAccount;
}
