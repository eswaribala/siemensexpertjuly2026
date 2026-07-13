package com.siemens.useraccountapi.dtos;

import com.siemens.useraccountapi.models.FullName;
import com.siemens.useraccountapi.models.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountRequest {

    private FullNameRequest fullName;

    private Gender gender;

    @Email(message = "Enter valid mail address")
    private String email;


    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{5,10}$",message = "Password must have mininum one digit,one lowercase and one upper case")
    private String password;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dob;
}
