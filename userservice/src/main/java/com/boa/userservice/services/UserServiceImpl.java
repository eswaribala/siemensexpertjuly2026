package com.boa.userservice.services;

import com.boa.userservice.dtos.CreateUserRequest;
import com.boa.userservice.dtos.UpdateUserRequestDTO;
import com.boa.userservice.exceptions.RoleNotFoundException;
import com.boa.userservice.exceptions.UserNotFoundExcepion;
import com.boa.userservice.models.FullName;
import com.boa.userservice.models.Role;
import com.boa.userservice.models.User;
import com.boa.userservice.repositories.RoleRepository;
import com.boa.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User createUser(CreateUserRequest createUserRequest) {

        List<Role> roles = new ArrayList<>();
        List<Role> persisitedRoles=null;
      if(createUserRequest.getRoles().isEmpty()){
          roles=null;

      }else {
          //transient
          roles = createUserRequest.getRoles();
          //persistent role
          //table level
          persisitedRoles=roles.stream()
                  .map(role->roleRepository.findById(role.getRoleId())
                  .orElseThrow(()->new RoleNotFoundException(role.getRoleId())))
                  .collect(Collectors.toList());


      }

      User user=null;

      if(persisitedRoles==null){
          user = User.builder()
                  .fullName(FullName.builder()
                          .firstName(createUserRequest.getFullNameDTO().getFirstName())
                          .lastName(createUserRequest.getFullNameDTO().getLastName())
                          .middleName(createUserRequest.getFullNameDTO().getMidddleName())

                          .build())
                  .email(createUserRequest.getEmail())
                  .roles(null)
                  .build();
      }else {


          user = User.builder()
                  .fullName(FullName.builder()
                          .firstName(createUserRequest.getFullNameDTO().getFirstName())
                          .lastName(createUserRequest.getFullNameDTO().getLastName())
                          .middleName(createUserRequest.getFullNameDTO().getMidddleName())

                          .build())
                  .email(createUserRequest.getEmail())
                  .roles(persisitedRoles)
                  .build();
      }

        return this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return this.userRepository.findById(id)
                .orElseThrow(()->new UnableToSendNotificationException(id));
    }

    @Override
    public boolean deleteUserById(String id) {
        boolean status=false;
        User user=getUserById(id);
        if(user!=null){
            this.userRepository.delete(user);
            status=true;
        }
        return status;
    }

    @Override
    public User updateUser(UpdateUserRequestDTO updateUserRequestDTO) {
        User  user=getUserById(updateUserRequestDTO.getUserId());
        if(user!=null){
            user.setEmail(updateUserRequestDTO.getEmail());
            return  this.userRepository.save(user);
        }else
            throw new UserNotFoundExcepion(updateUserRequestDTO.getUserId());
    }
}
