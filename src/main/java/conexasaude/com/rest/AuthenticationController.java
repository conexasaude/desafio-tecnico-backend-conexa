package conexasaude.com.rest;


import conexasaude.com.domain.dto.doctor.DoctorUserCreateDto;
import conexasaude.com.domain.dto.doctor.DoctorUserLoginRequestDto;
import conexasaude.com.domain.dto.doctor.DoctorUserLoginResponseDto;
import conexasaude.com.domain.entity.DoctorUser;
import conexasaude.com.domain.service.AuthenticationService;
import conexasaude.com.domain.service.ModelMapperService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final ModelMapperService modelMapperService;
    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid DoctorUserCreateDto doctorUserCreateDto) {
        authenticationService.save(
                modelMapperService.toObject(DoctorUser.class, doctorUserCreateDto)
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<DoctorUserLoginResponseDto> login(@RequestBody DoctorUserLoginRequestDto doctorUserLoginRequestDto) {
        return ResponseEntity.ok(
                authenticationService.login(doctorUserLoginRequestDto.getEmail(), doctorUserLoginRequestDto.getPassword())
        );
    }

    @PostMapping("/logoff")
    public ResponseEntity<Object> logoff(HttpServletRequest req) {
        authenticationService.logoff(req);
        return ResponseEntity.ok().build();
    }

}
