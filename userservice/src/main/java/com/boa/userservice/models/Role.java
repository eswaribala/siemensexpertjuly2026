package com.boa.userservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name="Bofa_Role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.AUTO) //sequence
    @Column(name="role_id")
    private long roleId;

    @Column(name="role_name",nullable=false,length=100,unique=true)
    private String roleName;
}
