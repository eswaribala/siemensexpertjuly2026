package com.siemens.useraccountapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Table(name="Role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Role_Id")
    private int roleId;
    @Column(name="Role_Name",nullable = false,length = 100)
    private String roleName;
    @ManyToMany(mappedBy = "roles")
    @Schema(hidden = true)
    private List<User> users;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
