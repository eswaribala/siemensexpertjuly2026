package com.siemens.useraccountapi;

import com.github.javafaker.Faker;
import com.siemens.useraccountapi.models.Role;
import com.siemens.useraccountapi.models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.ArrayList;
import java.util.List;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableCaching
//@EnableDiscoveryClient
public class UserAccountApiApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(UserAccountApiApplication.class, args);
    }

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
        userList.stream().forEach(System.out::println);

    }
}
