package com.boa.userservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="Bofa_User")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id //primarkey
    @UserId // auto generation
    @Column(name="user_id")
    private String userId;

    @Embedded
    private FullName fullName;

    @Column(name="email",nullable=false,length=150,unique=true)
    private String email;

    @ManyToMany
    @JoinTable(name="user_role",
            joinColumns = @JoinColumn(name="user_id",foreignKey =@ForeignKey(name="fk_user_roles_user")),
            inverseJoinColumns = @JoinColumn(name="role_id", foreignKey = @ForeignKey(name="fk_reoles_role")),
            uniqueConstraints = @UniqueConstraint(name="uk_user_roles_use_role",columnNames = {"user_id","role_id"})

    )

    private List<Role> roles;




}
