package br.com.conexa.domain.attendance;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Table(name = "attendance")
@Entity(name = "attendance")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHora;

    @NotEmpty(message = "Nome não pode ser vazio")
    @NotBlank(message = "Nome não pode ser vazio")
    @Length(min = 5, max = 255, message = "Nome só pode conter de 5 a 255 caracteres.")
    private  String nome;

    @NotEmpty(message = "CPF não pode ser vazio")
    @CPF
    private  String cpf;

    public Attendance(AttendanceRequestDTO data) {
        this.dataHora = data.dataHora();
        this.cpf = data.paciente().cpf();
        this.nome = data.paciente().nome();
    }
}