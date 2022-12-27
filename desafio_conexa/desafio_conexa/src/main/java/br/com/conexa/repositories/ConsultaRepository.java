package br.com.conexa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.conexa.model.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer>{


}
