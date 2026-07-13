package com.siemens.useraccountapi.services;

import com.siemens.useraccountapi.models.Role;
import com.siemens.useraccountapi.models.User;
import com.siemens.useraccountapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserJWTServiceImpl implements UserJWTService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findById(userName).orElse(null);
    }

    @Override
    public List<Role> getRoles(String userName) {
        User user=this.getUserByUserName(userName);
        if(user!=null)
            return user.getRoles();
        else
            return null;
    }
}
