package com.siemens.useraccountapi;

import com.siemens.useraccountapi.models.FullName;
import com.siemens.useraccountapi.models.Gender;
import com.siemens.useraccountapi.models.UserAccount;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

public class UserAccountTest {

    private static UserAccount userAccount;

    @BeforeAll
    public static void generateInstance(){
        userAccount=new UserAccount();
    }

    @Test
    public void userAccountNullorNotTest(){
        assertNotNull(userAccount);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "useraccount.csv", numLinesToSkip = 1)
    public void userAccountNameLengthGreaterThanThreeTest(String userId,String firstName,String lastName,String gender,String email, String password){

        userAccount.setUserId(userId);
        userAccount.setFullName(new FullName(firstName,lastName));
        userAccount.setGender(Enum.valueOf(Gender.class,gender));
        userAccount.setEmail(email);
        userAccount.setPassword(password);
       assertTrue(userAccount.getFullName().getFirstName().length()>3);
        assertAll(
                "Grouping more than one condition",
                () ->  assertTrue(userAccount.getEmail().contains("@") ),
                () ->  assertTrue(userAccount.getPassword().length()>4)
        );

    }

}
