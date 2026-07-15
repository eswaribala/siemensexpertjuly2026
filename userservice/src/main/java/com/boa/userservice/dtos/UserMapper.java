package com.boa.userservice.dtos;

import com.boa.userservice.models.Role;
import com.boa.userservice.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = { FullNameMapper.class })
public interface UserMapper{
    @Mapping(source = "userId", target = "id")
    @Mapping(source = "fullName", target = "fullNameDTO")
    UserDTO toDTO(User user);
    @Mapping(source = "roleId",target = "id")
    RoleDTO toDTO(Role role);
    List<RoleDTO> toDTOs(List<Role> roles);
    List<UserDTO> toUserDTOs(List<User> users);

}
