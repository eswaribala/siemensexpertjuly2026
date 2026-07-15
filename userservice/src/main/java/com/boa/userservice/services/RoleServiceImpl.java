package com.boa.userservice.services;

import com.boa.userservice.dtos.CreateRoleRequest;
import com.boa.userservice.dtos.UpdateRoleRequestDTO;
import com.boa.userservice.exceptions.RoleNotFoundException;
import com.boa.userservice.models.Role;
import com.boa.userservice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role createRole(CreateRoleRequest createRoleRequest) {

        Role role=Role.builder()
                .roleName(createRoleRequest.getRoleName())
                .build();
        return this.roleRepository.save(role);
    }

    @Override
    public List<Role> getRoles() {
        return this.roleRepository.findAll();
    }

    @Override
    public Role getRole(long roleId) {
        return this.roleRepository.findById(roleId)
                .orElseThrow(()->new RoleNotFoundException(roleId));
    }

    @Override
    public Role updateRole(UpdateRoleRequestDTO updateRoleRequestDTO) {

        Role role=this.getRole(updateRoleRequestDTO.getRoleId());
        if(role!=null){
            role.setRoleName(updateRoleRequestDTO.getRoleName());
            return this.roleRepository.save(role);
        }else {
            throw new RoleNotFoundException(updateRoleRequestDTO.getRoleId());
        }


    }

    @Override
    public boolean deleteRole(long roleId) {
       boolean status=false;
       Role role=this.getRole(roleId);
       if(role!=null){
           this.roleRepository.delete(role);
           status=true;
       }
       return status;
    }
}
