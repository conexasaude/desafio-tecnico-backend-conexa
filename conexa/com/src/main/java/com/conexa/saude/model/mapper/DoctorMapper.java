package  com.conexa.saude.model.mapper;

import org.mapstruct.Mapper;

import com.conexa.saude.model.dto.DoctorDTO;
import com.conexa.saude.model.dto.DoctorMinimalDTO;
import com.conexa.saude.model.dto.LoginDTO;
import com.conexa.saude.model.entity.Doctor;


@Mapper(componentModel = "spring")
public interface DoctorMapper {
    
    Doctor toMedico(DoctorDTO doctorDTO); 

    Doctor toLoginMedico(LoginDTO loginDTO);

    DoctorMinimalDTO toDoctorMinimal(Doctor doctor);

}
