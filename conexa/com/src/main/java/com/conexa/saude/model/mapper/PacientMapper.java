package  com.conexa.saude.model.mapper;

import org.mapstruct.Mapper;

import com.conexa.saude.model.dto.PacientDTO;
import com.conexa.saude.model.entity.PacienteEntity;


@Mapper(componentModel = "spring")
public interface PacientMapper {
    
    PacienteEntity toPacient(PacientDTO pacientDTO); 

    PacientDTO toPacientDTO(PacienteEntity paciente);
    

}
