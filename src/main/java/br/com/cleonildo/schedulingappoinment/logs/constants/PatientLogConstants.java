package br.com.cleonildo.schedulingappoinment.logs.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatientLogConstants {
    public static final String PATIENT_LIST = "Patient list returned successfully.";
    public static final String PATIENT_ID_FOUND = "Patient id {} found.";
    public static final String PATIENT_WITH_ID_NOT_FOUND = "Patient with id {} not found!";
    public static final String PATIENT_SAVED_SUCCESSFULLY = "Patient saved successfully.";
    public static final String PATIENT_UPDATE_SUCCESSFULLY = "Patient update successfully.";
    public static final String PATIENT_DELETED_SUCCESSFULLY = "Patient deleted successfully.";


}
