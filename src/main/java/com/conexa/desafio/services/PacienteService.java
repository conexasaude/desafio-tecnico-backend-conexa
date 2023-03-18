package com.conexa.desafio.services;

import com.conexa.desafio.models.PacienteEntity;

public interface PacienteService {

    PacienteEntity buscarPacientePorNome(String nome);

    boolean pacienteJaExiste(String nome);
}
