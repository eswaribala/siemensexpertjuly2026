package com.boa.userservice.controllers;

import com.boa.userservice.dtos.*;
import com.boa.userservice.models.Role;
import com.boa.userservice.models.User;
import com.boa.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/v1.0")
    public ResponseEntity<GenericResponse> addUser(@RequestBody CreateUserRequest  createUserRequest){
        User user=this.userService.createUser(createUserRequest);
        if(user!=null){
            UserDTO userDTO=userMapper.toDTO(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse(userDTO));

        }else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse("User not created"));

    }

    @GetMapping("/v1.0")

    public ResponseEntity<GenericResponse> getUsers(){

        List<User> users=this.userService.getAllUsers();
        List<UserDTO> userDTOs=null;
        logger.info("email: {}", "eswari@gmail.com");

        if(users!=null){
            userDTOs=userMapper.toUserDTOs(users);
            userDTOs.stream().forEach((userDTO)->{
                logger.info(userDTO.fullNameDTO().getFirstName());

                logger.info(userDTO.email());
                logger.debug(userDTO.email());
            });
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(userDTOs));
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse("users not found"));


    }
}
