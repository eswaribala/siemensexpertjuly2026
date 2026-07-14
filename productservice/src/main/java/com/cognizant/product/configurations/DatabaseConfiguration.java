package com.cognizant.product.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(VaultConfiguration.class)
public class DatabaseConfiguration {
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	private DataSourceBuilder builder;
	private final VaultConfiguration vaultConfiguration;
	public DatabaseConfiguration(VaultConfiguration vaultConfiguration) {
		this.vaultConfiguration = vaultConfiguration;
	}
	
	@Bean
	public DataSource getDataSource() {
		builder = DataSourceBuilder.create();
		builder.username(vaultConfiguration.getMysqlusername());
		builder.password(vaultConfiguration.getMysqlpassword());
		builder.url(url);
		builder.driverClassName(driverClassName);
		return builder.build();
		
	}

}
