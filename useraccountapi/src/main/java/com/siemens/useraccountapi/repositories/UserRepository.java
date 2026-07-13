package com.siemens.useraccountapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.siemens.useraccountapi.models.User;
public interface UserRepository extends JpaRepository<User,String> {
}
