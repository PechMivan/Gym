package com.gym.gym.services;

import com.gym.gym.entities.Token;
import com.gym.gym.entities.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TokenService {


    List<Token> getAllTokensByUsername(String username);

    Token getToken(String token);

    void createToken(User user, String token);

    @Transactional
    Token save(Token token);

    @Transactional
    void saveAllTokens(List<Token> tokens);

    void revokeAllUserTokens(String username);

    String generateToken(long userId, String username, List<String> roles);
}
