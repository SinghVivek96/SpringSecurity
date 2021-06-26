package com.vivek.security.jwtPractice;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JWTSecretKeyPractice {
    @Autowired
    private final JWTConfigPractice jwtConfigPractice;


    public JWTSecretKeyPractice(JWTConfigPractice jwtConfigPractice) {
        this.jwtConfigPractice = jwtConfigPractice;
    }

    @Bean
    public SecretKey getSecretKeyFor_Signing(){
        return Keys.hmacShaKeyFor(jwtConfigPractice.getSecretkey().getBytes());
    }
}
