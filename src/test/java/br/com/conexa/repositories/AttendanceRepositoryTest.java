package br.com.conexa.repositories;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class AttendanceRepositoryTest {


    @Autowired
    EntityManager entityManager;

    @Autowired
    AttendanceRepository attendanceRepository;


    /*@Test
    @DisplayName("Should find user by email with sucess ")
    void findByEmailSuccess() {
        RegisterDTO reg= new RegisterDTO(EMAIL,SENHA,SENHA,ESPECIALIDADE,CPF,DATA_NASCIMENTO,TELEFONE);
        User usr = this.createUser(reg);
        Optional<UserDetails> user = userRepository.findByEmail(usr.getEmail());
        Assertions.assertThat(user.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should find user by email with fail ")
    void findByEmailFail() {
        RegisterDTO reg= new RegisterDTO("","","","","",null,"");
        User usr = this.createUser(reg);
        Optional<UserDetails> user = userRepository.findByEmail(usr.getEmail());
        Assertions.assertThat(user.isEmpty()).isTrue();
    }

    private User createUser(RegisterDTO usr) {

        String encryptedPassword = new BCryptPasswordEncoder().encode(usr.senha());
        String cpfWithoutSpecialChars = usr.cpf().replaceAll("[^0-9]", "");
        String telefoneWithoutSpecialChars = usr.telefone().replaceAll("[^0-9]", "");

        User newUser = new User(usr.email(), encryptedPassword, usr.especialidade(),cpfWithoutSpecialChars, usr.dataNascimento(),telefoneWithoutSpecialChars);

        this.entityManager.persist(newUser);
        return newUser;
    }

     */
}