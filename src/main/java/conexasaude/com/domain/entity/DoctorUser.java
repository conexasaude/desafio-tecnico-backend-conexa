package conexasaude.com.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@Builder(toBuilder = true)
@Entity
@Table(name = "doctor")
@NoArgsConstructor
@AllArgsConstructor
public class DoctorUser implements Serializable {

    public static final String ROLE_NAME = "ROLE_DOCTOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "specialty")
    private String specialty;

    @Column(name = "cpf", unique = true, nullable = false)
    private String cpf;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "phone")
    private String phone;

}