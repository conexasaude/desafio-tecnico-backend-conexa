package br.com.cleonildo.schedulingappoinment.logs.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AttendanceLogConstants {
    public static final String ATTENDANCE_LIST = "List of Attendances returns successfully.";
    public static final String ATTENDANCE_ID_FOUND = "Attendance id {} found.";
    public static final String ATTENDANCE_WITH_ID_NOT_FOUND = "Attendance with id {} not found!";
    public static final String ATTENDANCE_SAVED_SUCCESSFULLY = "Attendance saved successfully.";

}
