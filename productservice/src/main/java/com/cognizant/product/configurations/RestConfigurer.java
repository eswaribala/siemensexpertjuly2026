package com.cognizant.product.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfigurer {

	@Bean
	public RestClient getRestClient() {
		return RestClient.create();
	}
}
