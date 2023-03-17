package com.conexa.desafio.services;

import com.conexa.desafio.models.TokenEntity;

public interface TokenService {

    TokenEntity salvarToken(TokenEntity token);

    void removerToken(String token);

    Boolean tokenJaExiste(String token);
}
