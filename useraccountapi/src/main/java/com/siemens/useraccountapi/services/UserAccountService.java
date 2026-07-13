package com.siemens.useraccountapi.services;

import com.siemens.useraccountapi.exceptions.UserAccountNotFoundException;
import com.siemens.useraccountapi.models.UserAccount;
import org.apache.catalina.User;

import java.util.List;

public interface UserAccountService {

    UserAccount addUserAccount(UserAccount userAccount);
    List<UserAccount> getAllUserAccounts();
    UserAccount getUserAccountById(String userId);
    List<UserAccount> getUserAccountByEmail(String email);

    UserAccount updateUserAccount(String userId, String email);

    boolean deleteUserAccount(String userId);

}
