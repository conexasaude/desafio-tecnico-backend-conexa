package com.conexa.desafio.services;

import com.conexa.desafio.models.UsuarioEntity;

public interface UsuarioService {

    UsuarioEntity criaUsuario(UsuarioEntity usuarioEntity);

    //TODO: implementar tratamento de exceção
    UsuarioEntity buscarPorEmail(String email);

    Boolean usuarioJaExiste(String email);
}
