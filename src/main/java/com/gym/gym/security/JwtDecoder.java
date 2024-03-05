package com.gym.gym.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtDecoder {

    @Autowired
    JwtProperties properties;

    public DecodedJWT decode(String token){
        return JWT.require(Algorithm.HMAC256(properties.getSecretKey()))
                  .build()
                  .verify(token);
    }
}
