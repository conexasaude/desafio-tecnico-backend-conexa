package com.conexa.desafio.services;

import com.conexa.desafio.models.UsuarioEntity;
import com.conexa.desafio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;


    @Override
    public UsuarioEntity criaUsuario(UsuarioEntity usuarioEntity) {
        usuarioEntity.setSenha(bCryptPasswordEncoder.encode(usuarioEntity.getSenha()));
        return usuarioRepository.save(usuarioEntity);
    }

    @Override
    public UsuarioEntity buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
        //TODO: validação quando usuário for nulo
    }

    @Override
    public Boolean usuarioJaExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
