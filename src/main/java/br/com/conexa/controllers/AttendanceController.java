package br.com.conexa.controllers;

import br.com.conexa.domain.attendance.Attendance;
import br.com.conexa.domain.attendance.AttendanceRequestDTO;
import br.com.conexa.infra.security.TokenService;
import br.com.conexa.services.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping("api/v1")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/attendance")
    public ResponseEntity newAttendance(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody AttendanceRequestDTO body){
        String token = tokenService.extractToken(authorizationHeader);

        if (tokenService.isTokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }

        Optional<Attendance> newAttendance = attendanceService.newAttendance(body);
        return newAttendance.isPresent() ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
