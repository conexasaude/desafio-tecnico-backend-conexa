package br.com.cleonildo.schedulingappoinment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@JsonPropertyOrder({"id", "nome", "cpf"})
public class PatientResponse {
    private Long id;
    @JsonProperty("nome")
    private String name;
    private String cpf;

}
