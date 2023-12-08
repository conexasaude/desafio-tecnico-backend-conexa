package com.felipe.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipe.controller.PatientController;
import com.felipe.exceptions.BadRequestException;
import com.felipe.exceptions.ResourceNotFoundException;
import com.felipe.mapper.PatientMapper;
import com.felipe.model.Patient;
import com.felipe.model.dto.v1.PatientDTO;
import com.felipe.repositories.PatientRepository;
import com.felipe.util.MessageUtils;
import com.felipe.util.StringUtil;

/**
 * Service class for managing user-related operations.
 */
@Service
public class PatientService {
	private Logger logger = Logger.getLogger(PatientService.class.getName());

	@Autowired
	private PatientRepository repository;

	@Autowired
	private PatientMapper mapper;

    /**
     * Retrieves a list of all users.
     *
     * @return A list of PatientDTO objects.
     */
	public List<PatientDTO> findAll() {
		logger.info("Finding All Patient");

		List<PatientDTO> listPatients = mapper.toDto(repository.findAll());
		listPatients.stream().forEach(user -> {
			try {
				addPatientSelfRel(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return listPatients;
	}

    /**
     * Retrieves a user by their ID.
     *
     * @param id: The ID of the user.
     * @return The PatientDTO object representing the user.
     * @throws ResourceNotFoundException: If no user is found with the given ID.
     */
	public PatientDTO findById(String id) throws Exception {
		logger.info("Finding one user");

		PatientDTO user = repository.findById(UUID.fromString(id)).map(mapper::toDto)
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		return addPatientSelfRel(user);
	}

    /**
     * Creates a new user.
     *
     * @param dto: The PatientDTO object containing user information.
     * @return The created PatientDTO object.
     * @throws BadRequestException: If the email provided already exists.
     */
	public PatientDTO create(PatientDTO dto) throws Exception {
		logger.info("Create one user");

		repository.findByCpf(StringUtil.removeNonNumeric(dto.getCpf())).ifPresent(existingPatient -> {
			throw new BadRequestException("Email " + MessageUtils.RECORDS_ALREADY_EXIST + ": " + dto.getCpf() + " or " + StringUtil.removeNonNumeric(dto.getCpf()));
		});

		Patient entity = mapper.toEntity(dto);
		PatientDTO user = mapper.toDto(repository.save(entity));

		return addPatientSelfRel(user);

	}
    /**
     * Updates an existing user.
     *
     * @param dto: The PatientDTO object containing updated user information.
     * @return The updated PatientDTO object.
     * @throws ResourceNotFoundException: If no user is found with the given ID.
     */
	public PatientDTO update(PatientDTO dto) throws Exception {
		logger.info("Update one user");

		Patient entity = repository.findById(dto.getKey())
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		entity.setFullName(Objects.requireNonNullElse(dto.getFullName(), entity.getFullName()));
		entity.setCpf(Objects.requireNonNullElse(dto.getCpf(), entity.getCpf()));
		entity.setHealthInsurance(Objects.requireNonNullElse(dto.getHealthInsurance(), entity.getHealthInsurance()));

		PatientDTO user = mapper.toDto(repository.save(entity));
		return addPatientSelfRel(user);
	}

    /**
     * Deletes a user by their ID.
     *
     * @param id: The ID of the user to be deleted.
     * @throws ResourceNotFoundException: If no user is found with the given ID.
     */
	public void delete(String id) {
		logger.info("Deleting one user");
		Patient entity = repository.findById(UUID.fromString(id))
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		repository.delete(entity);
	}
	
    /**
     *     		
     * @param user: The Patient object containing user information
     * @return The PatientDTO with self-rel link 
     * @throws Exception
     */
	private PatientDTO addPatientSelfRel(PatientDTO user) throws Exception {
	    return (PatientDTO) user.add(linkTo(methodOn(PatientController.class).findById(user.getKey().toString())).withSelfRel());
	}
}
