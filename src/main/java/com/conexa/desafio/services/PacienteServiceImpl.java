package com.conexa.desafio.services;

import com.conexa.desafio.models.PacienteEntity;
import com.conexa.desafio.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public PacienteEntity buscarPacientePorNome(String nome) {
        Optional<PacienteEntity> byNome = pacienteRepository.findByNome(nome);
        return byNome.orElse(null);
    }

    @Override
    public boolean pacienteJaExiste(String nome) {
        return pacienteRepository.existsByNome(nome);
    }
}
