package com.siemens.useraccountapi.controllers;

import com.siemens.useraccountapi.dtos.GenericResponse;
import com.siemens.useraccountapi.dtos.UpdateUserAccountRequest;
import com.siemens.useraccountapi.dtos.UserAccountRequest;
import com.siemens.useraccountapi.models.FullName;
import com.siemens.useraccountapi.models.UserAccount;
import com.siemens.useraccountapi.services.UserAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("useraccounts")
@Validated
public class UserController {
    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/v1.0")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenericResponse> saveUserAccount(@Valid @RequestBody UserAccountRequest userAccountRequest){
           //DTO to Model

        UserAccount userAccount=UserAccount.builder()
                .fullName(FullName.builder()
                        .firstName(userAccountRequest.getFullName().getFirstName())
                        .lastName(userAccountRequest.getFullName().getLastName())
                        .build())
                .dob(userAccountRequest.getDob())
                .email(userAccountRequest.getEmail())
                .password(userAccountRequest.getPassword())
                .gender(userAccountRequest.getGender())
                .build();



        UserAccount userAccountInstance=this.userAccountService.addUserAccount(userAccount);
        if(userAccountInstance!=null)
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new GenericResponse(userAccountInstance));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse("User Account Not Created.... " +
                            "because of invalid data"));

    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/v1.0")
    public ResponseEntity<GenericResponse> fetchAllUserAccounts(){

        List<UserAccount> userAccountList=this.userAccountService.getAllUserAccounts();
        if(userAccountList.size()>0)
            return ResponseEntity.status(HttpStatus.OK).body(
                    new GenericResponse(userAccountList));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse("User Account Not Created Yet"));

    }
    @GetMapping("/v1.0/{userId}")
    public ResponseEntity<GenericResponse> fetchUserAccountByUserId(@PathVariable("userId") String userId){

        UserAccount userAccount=this.userAccountService.getUserAccountById(userId);
        if(userAccount!=null)
            return ResponseEntity.status(HttpStatus.OK).body(
                    new GenericResponse(userAccount));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse("User Account Not Found For the Given Id"+userId));

    }
    @GetMapping("/v1.0/filterbyemail/{email}")
    public ResponseEntity<GenericResponse> fetchUserAccountByEmail(@PathVariable("email") String email){

        List<UserAccount> userAccounts=this.userAccountService.getUserAccountByEmail(email);
        if(userAccounts.size()>0)
            return ResponseEntity.status(HttpStatus.OK).body(
                    new GenericResponse(userAccounts));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse("User Account Not Found For the Given Email"+email));

    }

    @PutMapping("/v1.0")
    public ResponseEntity<GenericResponse> updateUserAccountByUserId(@RequestBody UpdateUserAccountRequest updateUserAccountRequest){

        UserAccount userAccount=this.userAccountService.updateUserAccount(updateUserAccountRequest.getUserId(),updateUserAccountRequest.getEmail());

            return ResponseEntity.status(HttpStatus.OK).body(
                    new GenericResponse(userAccount));

    }

    @DeleteMapping("/v1.0/{userId}")
    public ResponseEntity<GenericResponse> deleteUserAccountByUserId(@PathVariable("userId") String userId){


        if(this.userAccountService.deleteUserAccount(userId))
            return ResponseEntity.status(HttpStatus.OK).body(
                    new GenericResponse("User Account Found and Deleted for Id"+userId));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse("User Account Not Found For the Given Id"+userId));

    }


}
