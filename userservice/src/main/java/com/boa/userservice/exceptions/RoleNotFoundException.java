package com.boa.userservice.exceptions;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(long roleId) {
        super("Role with id " + roleId + " not found");
    }
}
