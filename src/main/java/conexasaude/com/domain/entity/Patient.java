package conexasaude.com.domain.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder(toBuilder = true)
@Entity
@Table(name = "patient")
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cpf")
    private String cpf;


}
