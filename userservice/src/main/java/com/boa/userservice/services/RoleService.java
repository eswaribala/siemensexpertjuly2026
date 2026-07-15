package com.boa.userservice.services;

import com.boa.userservice.dtos.CreateRoleRequest;
import com.boa.userservice.dtos.UpdateRoleRequestDTO;
import com.boa.userservice.models.Role;

import java.util.List;

public interface RoleService {

    Role createRole(CreateRoleRequest createRoleRequest);
    List<Role> getRoles();
    Role getRole(long roleId);
    Role updateRole(UpdateRoleRequestDTO updateRoleRequestDTO);
    boolean deleteRole(long roleId);
}
