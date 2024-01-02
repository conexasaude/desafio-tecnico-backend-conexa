package conexasaude.com.rest;

import conexasaude.com.domain.dto.attendance.AttendanceCreateDto;
import conexasaude.com.domain.dto.attendance.AttendanceDetailsDto;
import conexasaude.com.domain.entity.Attendance;
import conexasaude.com.domain.service.AttendanceService;
import conexasaude.com.domain.service.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final ModelMapperService modelMapperService;

    @PostMapping
    public ResponseEntity<Object> createAttendance(@RequestBody AttendanceCreateDto dto) {
        return ResponseEntity.ok(modelMapperService.toObject(AttendanceDetailsDto.class,
            attendanceService.createAttendance(modelMapperService.toObject(Attendance.class, dto))
        ));
    }

}
