/*
package com.boa.userservice.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
//@EnableConfigurationProperties(VaultConfiguration.class)
public class DatabaseConfiguration {
    @Value("${drivername}")
    private String driverName;
    @Value("${url}")
    private String url;
    //private final VaultConfiguration vaultConfiguration;

    @Value("${mysqlusername}")
    private String userName;
    @Value("${mysqlpassword}")
    private String  password;

    private DataSourceBuilder dataSourceBuilder;

//    public DatabaseConfiguration(VaultConfiguration _vaultConfiguration) {
//        this.vaultConfiguration = _vaultConfiguration;
//    }

    @Bean
    public DataSource createDataSource(){
        dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(userName);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}
*/
