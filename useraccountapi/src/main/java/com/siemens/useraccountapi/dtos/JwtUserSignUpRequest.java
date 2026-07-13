package com.siemens.useraccountapi.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.siemens.useraccountapi.models.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserSignUpRequest {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First Name must contain only alphabets")

    private String userName;
    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{5,10}$",message = "Password must have mininum one digit,one lowercase and one upper case")

    private String password;

    private List<Role> roles;
}
