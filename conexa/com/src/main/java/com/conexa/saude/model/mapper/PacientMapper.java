package  com.conexa.saude.model.mapper;

import org.mapstruct.Mapper;

import com.conexa.saude.model.dto.PacientDTO;
import com.conexa.saude.model.entity.Paciente;


@Mapper(componentModel = "spring")
public interface PacientMapper {
    
    Paciente toPacient(PacientDTO pacientDTO); 

    PacientDTO toPacientDTO(Paciente paciente);
    

}
