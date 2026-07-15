package com.boa.userservice.graphql;


import com.boa.userservice.models.FullName;
import com.boa.userservice.models.Role;
import com.boa.userservice.models.User;
import com.boa.userservice.repositories.RoleRepository;
import com.boa.userservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class UserRoleGraphQL {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    /* =======================
                 QUERIES
       ======================= */

    @QueryMapping
    public List<User> users() {
        return userRepo.findAll(); // @EntityGraph fetches roles to prevent N+1 for listing
    }

    @QueryMapping
    public User userById(@Argument String id) {
        return userRepo.findById(id).orElse(null);

    }

    @QueryMapping
    public List<Role> roles() {
        return roleRepo.findAll();
    }

    @QueryMapping
    public Role roleById(@Argument Long id) {
        return roleRepo.findById(id).orElse(null);
    }

    /* =======================
               MUTATIONS
       ======================= */

    @MutationMapping
    @Transactional
    public Role createRole(@Argument String roleName) {
        return roleRepo.findByRoleName(roleName)
                .orElseGet(() -> roleRepo.save(Role.builder().roleName(roleName).build()));
    }

    public record FullNameInput(String firstName, String lastName, String middleName) {}

    @MutationMapping
    @Transactional
    public User createUser(@Argument FullNameInput fullName,
                           @Argument String email,
                           @Argument List<Long> roleIds) {

        List<Role> roles = (roleIds == null || roleIds.isEmpty())
                ? Collections.emptyList()
                : roleRepo.findAllById(roleIds);

        User u = User.builder()
                .fullName(new FullName(fullName.firstName(),fullName.lastName(),fullName.middleName()))
                .email(email)
                .roles(new ArrayList<>(roles))
                .build();

        return userRepo.save(u);
    }

    @MutationMapping
    @Transactional
    public User assignRolesToUser(@Argument String userId,
                                  @Argument List<Long> roleIds) {

        User user = userRepo.findById(userId).orElse(null);
        if (user == null) throw new NoSuchElementException("User not found: " + userId);

        List<Role> roles = roleRepo.findAllById(roleIds);
        // merge: keep existing + new, avoid duplicates
        Map<Long, Role> byId = new HashMap<>();
        user.getRoles().forEach(r -> byId.put(r.getRoleId(), r));
        roles.forEach(r -> byId.put(r.getRoleId(), r));
        user.setRoles(new ArrayList<>(byId.values()));

        return userRepo.save(user);
    }

    @MutationMapping
    @Transactional
    public User updateUserEmail(@Argument String userId, @Argument String email) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found: " + userId));
        user.setEmail(email);
        return userRepo.save(user);
    }

    @MutationMapping
    @Transactional
    public Boolean deleteUser(@Argument String userId) {
        if (!userRepo.existsById(userId)) return false;
        userRepo.deleteById(userId);
        return true;
    }

    /* =======================
         FIELD RESOLVERS
       ======================= */
    // Not strictly required because we already join-fetch roles in queries above,
    // but this shows how you'd resolve nested fields explicitly.
    @SchemaMapping(typeName = "User", field = "roles")
    public List<Role> roles(User user) {
        return user.getRoles(); // already initialized via join fetch
    }

}


