package br.com.cleonildo.schedulingappoinment.logs.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DoctorLogConstants {
    public static final String DOCTOR_LIST = "List of doctors returns successfully.";
    public static final String DOCTOR_ID_FOUND = "Doctor id {} found.";
    public static final String DOCTOR_WITH_ID_NOT_FOUND = "Doctor with id {} not found!";
    public static final String DOCTOR_PASSWORDS_DOES_NOT_MATCH = "Password does not match!";
    public static final String DOCTOR_UPDATE_SUCCESSFULLY = "Doctor update successfully.";
    public static final String DOCTOR_SAVED_SUCCESSFULLY = "Doctor saved successfully.";
    public static final String DOCTOR_DELETED_SUCCESSFULLY = "Doctor deleted successfully.";

}
