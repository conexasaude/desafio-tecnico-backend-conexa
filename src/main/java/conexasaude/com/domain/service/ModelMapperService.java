package conexasaude.com.domain.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModelMapperService {

    private final ModelMapper modelMapper;

    public <T> T toObject(Class<T> clazz, Object item) {
        if (item == null) return null;
        return modelMapper.map(item, clazz);
    }

}
