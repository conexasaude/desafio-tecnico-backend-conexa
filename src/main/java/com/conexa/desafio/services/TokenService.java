package com.conexa.desafio.services;

import com.conexa.desafio.models.TokenEntity;
import com.conexa.desafio.models.UsuarioEntity;

public interface TokenService {

    TokenEntity salvarToken(TokenEntity token);

    void removerToken(String token);

    Boolean tokenJaExiste(String token);

    void removerTokenDoUsuario(UsuarioEntity usuario);

    UsuarioEntity buscarUsuarioPorToken(String token);
}
