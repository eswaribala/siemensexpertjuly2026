package com.cognizant.customerservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JWTConfig {

    @Bean
    public JwtDecoder jwtDecoder() {
        // Keycloak JWK endpoint for the "master" realm
        String jwkSetUri = "http://localhost:8080/realms/master/protocol/openid-connect/certs";
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }
}