package br.com.cleonildo.schedulingappoinment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Specialty {
    CARDIOLOGIST("Cardiologista"),
    DERMATOLOGIST("Dermatologista"),
    ENDOCRINOLOGIST("Endocrinologista"),
    GASTROENTEROLOGIST("Gastroenterologista"),
    NEUROLOGIST("Neurologista"),
    OPHTHALMOLOGIST("Oftalmologista"),
    ORTHOPEDIST("Ortopedista"),
    PEDIATRICIAN("Pediatra"),
    PSYCHIATRIST("Psiquiatra"),
    UROLOGIST("Urologista");

    private final String description;

}
