package br.com.conexa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.conexa.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer>{

	Medico findByEmail(String email);

}
