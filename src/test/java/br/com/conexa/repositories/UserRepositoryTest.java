package br.com.conexa.repositories;

import br.com.conexa.domain.user.RegisterDTO;
import br.com.conexa.domain.user.User;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static br.com.conexa.constants.Constants.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {


    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("Should find user by email with sucess ")
    void findByEmailSuccess() {
        RegisterDTO reg= new RegisterDTO(EMAIL,SENHA,SENHA,ESPECIALIDADE,CPF,DATA_NASCIMENTO,TELEFONE);
        User usr = this.createUser(reg);
        Optional<UserDetails> user = this.userRepository.findByEmail(usr.getEmail());
        Assertions.assertThat(user.isPresent()).isTrue();
    }

    private User createUser(RegisterDTO usr) {
        User newUser = new User(usr.email(), usr.senha(), usr.especialidade(),usr.cpf(), usr.dataNascimento(),usr.telefone());
        this.entityManager.persist(newUser);
        return newUser;
    }


}