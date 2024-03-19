package com.gym.gym.services.implementations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gym.gym.entities.Token;
import com.gym.gym.entities.TokenType;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TokenRepository;
import com.gym.gym.services.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Value("${security.jwt.secret-key}")
    String secretKey;

    @Override
    public List<Token> getAllTokensByUsername(String username){
        return tokenRepository.findAllValidTokensByUser(username);
    }

    @Override
    public Token getToken(String token){
        return tokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException(String.format("Token with value [%s] not found.", token)));
    }

    @Override
    public Token createToken(User user, String jwtToken){
        Token token = Token.builder()
                           .user(user)
                           .token(jwtToken)
                           .tokenType(TokenType.BEARER)
                           .expired(false)
                           .revoked(false)
                           .build();
        save(token);
        return token;
    }

    @Transactional
    @Override
    public Token save(Token token){
        return tokenRepository.save(token);
    }

    @Transactional
    @Override
    public void saveAllTokens(List<Token> tokens){
        tokenRepository.saveAll(tokens);
    }

    @Override
    public void revokeAllUserTokens(String username){
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(username);
        if(validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        saveAllTokens(validUserTokens);
    }

    @Override
    public String generateToken(long userId, String username, List<String> roles){
        revokeAllUserTokens(username);
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(5, ChronoUnit.MINUTES)))
                .withClaim("u", username)
                .withClaim("a", roles)
                .sign(Algorithm.HMAC256(secretKey));
    }
}
