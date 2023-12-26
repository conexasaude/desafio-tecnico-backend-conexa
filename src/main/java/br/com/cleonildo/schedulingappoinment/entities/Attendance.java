package br.com.cleonildo.schedulingappoinment.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "attendance")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Attendance implements Serializable {
    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private Instant dateHour;
    @OneToOne
    private Patient patient;

    public Attendance(Instant dateHour, Patient patient) {
        this.dateHour = dateHour;
        this.patient = patient;
    }
}
