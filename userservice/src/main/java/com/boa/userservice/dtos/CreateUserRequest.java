package com.boa.userservice.dtos;

import com.boa.userservice.models.FullName;
import com.boa.userservice.models.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {


   private FullNameDTO fullNameDTO;

    @Email(message = "Invalid Email Address")
    @NotNull
    private String email;



    private List<Role> roles;
}
