package com.conexa.desafio.services;

import com.conexa.desafio.models.ConsultaEntity;
import com.conexa.desafio.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaServiceImpl implements ConsultaService{

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public ConsultaEntity salvarConsulta(ConsultaEntity consulta) {
        return consultaRepository.save(consulta);
    }
}
