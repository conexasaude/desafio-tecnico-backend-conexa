package com.conexa.desafio.services;

import com.conexa.desafio.models.TokenEntity;
import com.conexa.desafio.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService{

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public TokenEntity salvarToken(TokenEntity token) {
        return tokenRepository.save(token);
    }

    @Override
    public void removerToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    @Override
    public Boolean tokenJaExiste(String token) {
        return tokenRepository.existsByToken(token);
    }
}
