package com.siemens.useraccountapi.services;
import com.siemens.useraccountapi.models.Role;
import com.siemens.useraccountapi.models.User;

import java.util.List;

public interface UserJWTService {

    User addUser(User user);
    List<User> getAllUsers();
    User getUserByUserName(String userName);
    List<Role> getRoles(String userName);

}
