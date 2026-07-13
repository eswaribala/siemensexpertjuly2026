package com.siemens.useraccountapi.services;

import com.siemens.useraccountapi.models.Role;
import com.siemens.useraccountapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JWTUserAuthService implements UserDetailsService {
    @Autowired
    private UserJWTService userJWTService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userJWTService.getUserByUserName(username);
        if(user==null){
            throw new UsernameNotFoundException("User Not Available");
        }else {
          List<Role> roles=userJWTService.getRoles(user.getUserName());
            List<GrantedAuthority> grantedAuthorities = roles.stream().map(r -> {
                return new SimpleGrantedAuthority(r.getRoleName());
            }).collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                    grantedAuthorities);
        }


    }
}
