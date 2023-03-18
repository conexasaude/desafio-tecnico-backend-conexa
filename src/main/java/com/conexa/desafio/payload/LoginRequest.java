package com.conexa.desafio.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Email(message = "{email.invalid}")
    @NotNull(message = "{email.not.null}")
    @NotEmpty(message = "{email.not.empty}")
    @NotBlank(message = "{email.not.blank}")
    private String email;

    @NotNull(message = "{senha.not.null}")
    @NotEmpty(message = "{senha.not.empty}")
    @NotBlank(message = "{senha.not.blank}")
    private String senha;

}
