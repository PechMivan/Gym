package com.gym.gym.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class JwtIssuer {

    @Autowired
    JwtProperties properties;

    public String issue(long userId, String username, List<String> roles){
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.MINUTES)))
                .withClaim("u", username)
                .withClaim("a", roles)
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }
}
