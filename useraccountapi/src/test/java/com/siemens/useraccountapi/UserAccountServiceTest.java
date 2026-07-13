package com.siemens.useraccountapi;

import com.github.javafaker.Faker;
import com.siemens.useraccountapi.models.FullName;
import com.siemens.useraccountapi.models.Gender;
import com.siemens.useraccountapi.models.UserAccount;
import com.siemens.useraccountapi.repositories.UserAccountRepository;
import com.siemens.useraccountapi.services.UserAccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserAccountServiceTest {

    @InjectMocks
    private UserAccountServiceImpl userAccountService;
    @Mock
    private UserAccountRepository userAccountRepository;

    @Test
    public void findAllUserAccountsTest(){

        when(userAccountRepository.findAll()).thenReturn( generateUserAccounts());

        //test
        List<UserAccount> userAccountListList = userAccountService.getAllUserAccounts();

        assertEquals(100, userAccountListList.size());
        verify(userAccountRepository, times(1)).findAll();


    }


    private List<UserAccount> generateUserAccounts(){
        List<UserAccount> userAccountList=new ArrayList<>();
        Faker faker=new Faker();
        for(int i=0;i<100;i++){
            userAccountList.add(new UserAccount(faker.internet().uuid(),
                    new FullName(faker.name().firstName(),faker.name().lastName()),
                    getRandomEnumValue(Gender.class),
                    faker.internet().emailAddress(),
                    faker.internet().password(),
                    Instant.ofEpochMilli(faker.date().birthday().getTime()).atZone(ZoneId.systemDefault()).toLocalDate()
                    ));

        }
        return userAccountList;


    }

    public static <T extends Enum<?>> T getRandomEnumValue(Class<T> enumClass){
        T[] enumValues=enumClass.getEnumConstants();
        return enumValues[new Random().nextInt(enumValues.length)];
    }

}
