package br.com.cleonildo.schedulingappoinment.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionsConstants {
    public static final String PATIENT_ID_NOT_FOUND = "Patient not found!";
    public static final String DOCTOR_ID_NOT_FOUND = "Doctor not found!";

}
