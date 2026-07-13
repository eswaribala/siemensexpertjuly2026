package com.siemens.useraccountapi.configurations;

import com.github.javafaker.Faker;
import com.siemens.useraccountapi.models.Role;
import com.siemens.useraccountapi.models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DbDataGenerator implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        List<User> userList=new ArrayList<>();
        List<Role> roleList=null;
        Faker faker=new Faker();
        for(int i=0;i<100;i++){
            roleList=new ArrayList<>();
            roleList.add(new Role(faker.random().nextInt(1,100000),"ROLE_ADMIN",null));
            userList.add(new User(faker.name().firstName(),faker.internet().password(),roleList));
        }
        System.out.println("Generated from Db Data Generator.........");
        userList.stream().forEach(System.out::println);
    }
}
