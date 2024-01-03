package br.com.conexa.domain.attendance;

import br.com.conexa.utils.FutureTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record AttendanceRequestDTO(
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @FutureTime
        LocalDateTime dataHora,

        PacienteRequestDTO paciente
) {
}
