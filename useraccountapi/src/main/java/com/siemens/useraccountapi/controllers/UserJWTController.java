package com.siemens.useraccountapi.controllers;

import com.siemens.useraccountapi.configurations.JWTUtil;
import com.siemens.useraccountapi.dtos.GenericResponse;
import com.siemens.useraccountapi.dtos.JWTRequest;
import com.siemens.useraccountapi.dtos.JwtUserSignUpRequest;
import com.siemens.useraccountapi.dtos.UserAccountRequest;
import com.siemens.useraccountapi.models.FullName;
import com.siemens.useraccountapi.models.Role;
import com.siemens.useraccountapi.models.User;
import com.siemens.useraccountapi.models.UserAccount;
import com.siemens.useraccountapi.services.JWTUserAuthService;
import com.siemens.useraccountapi.services.UserJWTService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserJWTController {
    @Autowired
    private UserJWTService userJWTService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUserAuthService jwtUserAuthService;
    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("signup/v1.0")
    public ResponseEntity<GenericResponse> saveUser(@Valid @RequestBody JwtUserSignUpRequest jwtUserSignUpRequest){
        //DTO to Model
        System.out.println(jwtUserSignUpRequest.getUserName()+","+jwtUserSignUpRequest.getPassword());
        User user=User.builder()
                .userName(jwtUserSignUpRequest.getUserName())
                .password(jwtUserSignUpRequest.getPassword())
                .roles(jwtUserSignUpRequest.getRoles())
                .build();

        User userExistence=userJWTService.getUserByUserName(user.getUserName());
    if(userExistence==null) {
        User userInstance = this.userJWTService.addUser(user);
        if (userInstance != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new GenericResponse(userInstance));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse("User Not Created.... " +
                            "because of invalid data"));
    }else
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GenericResponse("User Already Exists"));

    }
    @PostMapping("signin/v1.0")
    public ResponseEntity<GenericResponse> login(@Valid @RequestBody JWTRequest jwtRequest) {
        //DTO to Model
        User user = User.builder()
                .userName(jwtRequest.getUserName())
                .password(jwtRequest.getPassword())
                .build();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        }catch (DisabledException disabledException){
           System.out.println(disabledException.getMessage());
        }catch (BadCredentialsException badCredentialsException){
           System.out.println(badCredentialsException);
        }

        UserDetails userDetails=jwtUserAuthService.loadUserByUsername(user.getUserName());

        User verifiedUser=new User();
        verifiedUser.setUserName(userDetails.getUsername());
        verifiedUser.setPassword(userDetails.getPassword());
        List<Role> roles= new ArrayList(userDetails
                .getAuthorities()
                .stream()
                .map(r->r.getAuthority())
                .collect(Collectors.toList()));
        verifiedUser.setRoles(roles);

       String token= jwtUtil.generateToken(verifiedUser);
       if(token!=null){
           return ResponseEntity.status(HttpStatus.OK).body(
                   new GenericResponse(token));
       }else
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                   new GenericResponse("Token Not generated"));

    }

}
