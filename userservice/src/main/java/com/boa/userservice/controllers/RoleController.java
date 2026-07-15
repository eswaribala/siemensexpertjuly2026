package com.boa.userservice.controllers;

import com.boa.userservice.dtos.*;
import com.boa.userservice.models.Role;
import com.boa.userservice.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("roles")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserMapper  userMapper;

    @PostMapping("/v1.0")
    public ResponseEntity<GenericResponse> addRole(@RequestBody CreateRoleRequest createRoleRequest) {

        Role role=this.roleService.createRole(createRoleRequest);
        RoleDTO roleDTO=null;
        if(role!=null){
            roleDTO=userMapper.toDTO(role);
            return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse(roleDTO));
        }else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse("Role not created"));


    }


    @GetMapping("/v1.0")

    public ResponseEntity<GenericResponse> getRoles(){

        List<Role> roles=this.roleService.getRoles();
        List<RoleDTO> roleDTOs=null;
        if(roles!=null){
           roleDTOs=userMapper.toDTOs(roles);
           logger.debug("roleDTOs: "+roleDTOs);
           logger.info("roleDTOs: "+roleDTOs);
           return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(roleDTOs));
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse("Roles not found"));


    }

    //get role by id

    @GetMapping("/v1.0/{id}")
    public ResponseEntity<GenericResponse> getRoleById(@PathVariable("id") Long id){

        Role role=this.roleService.getRole(id);
        RoleDTO roleDTO=null;
        if(role!=null){
            roleDTO=userMapper.toDTO(role);
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(roleDTO));
        }else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse("Role not found"));


    }


    @PutMapping("/v1.0")
    public ResponseEntity<GenericResponse> updateRoleById(@RequestParam("id") Long id, @RequestParam("roleName") String newRoleName){

        UpdateRoleRequestDTO  updateRoleRequestDTO=UpdateRoleRequestDTO
                .builder()
                .roleId(id)
                .roleName(newRoleName).build();
        Role role=this.roleService.updateRole(updateRoleRequestDTO);
        RoleDTO roleDTO=null;
        if(role!=null){
            roleDTO=userMapper.toDTO(role);
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(roleDTO));
        }else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse("Role not updated"));
    }
    @DeleteMapping("/v1.0/{id}")
    public ResponseEntity<GenericResponse> deleteRoleById(@PathVariable("id") Long id){

        if(this.roleService.deleteRole(id)){
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse("Role deleted "+id));
        }else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse("Role not delete d"+id));



    }

}
