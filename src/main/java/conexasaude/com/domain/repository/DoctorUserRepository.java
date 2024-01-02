package conexasaude.com.domain.repository;


import conexasaude.com.domain.entity.DoctorUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorUserRepository extends JpaRepository<DoctorUser, Long> {
    Optional<DoctorUser> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}