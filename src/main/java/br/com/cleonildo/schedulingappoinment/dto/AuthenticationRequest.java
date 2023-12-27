package br.com.cleonildo.schedulingappoinment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record AuthenticationRequest(
        @Email
        String email,
        @NotEmpty
        @NotBlank
        @JsonProperty("senha")
        String password
) {
}
