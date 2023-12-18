package br.com.cleonildo.schedulingappoinment.entities;

import br.com.cleonildo.schedulingappoinment.enums.Specialty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "doctor")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Specialty specialty;
    @Column(unique = true, nullable = false)
    private String cpf;
    private LocalDate birthdate;
    private String phone;

    public Doctor(String email, String password, Specialty specialty, String cpf, LocalDate birthdate, String phone) {
        this.email = email;
        this.password = password;
        this.specialty = specialty;
        this.cpf = cpf;
        this.birthdate = birthdate;
        this.phone = phone;
    }
}