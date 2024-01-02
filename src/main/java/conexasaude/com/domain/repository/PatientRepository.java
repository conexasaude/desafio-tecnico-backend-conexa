package conexasaude.com.domain.repository;

import conexasaude.com.domain.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByCpf(String cpf);
}
