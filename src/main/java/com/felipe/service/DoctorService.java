package com.felipe.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipe.controller.DoctorController;
import com.felipe.exceptions.BadRequestException;
import com.felipe.exceptions.ResourceNotFoundException;
import com.felipe.mapper.DoctorMapper;
import com.felipe.model.Doctor;
import com.felipe.model.dto.v1.DoctorDTO;
import com.felipe.repositories.DoctorRepository;
import com.felipe.util.DateUtil;
import com.felipe.util.MessageUtils;


@Service
public class DoctorService {
	private Logger logger = Logger.getLogger(DoctorService.class.getName());

	@Autowired
	private DoctorRepository repository;

	@Autowired
	private DoctorMapper mapper;

    /**
     * Retrieves a list of all doctors.
     *
     * @return A list of DoctorDTO objects.
     */
	public List<DoctorDTO> findAll() {
		logger.info("Finding All Doctor");

		List<DoctorDTO> listDoctors = mapper.toDto(repository.findAll());
		listDoctors.stream().forEach(doctor -> {
			try {
				addDoctorSelfRel(doctor);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return listDoctors;
	}

    /**
     * Retrieves a doctor by their ID.
     *
     * @param id: The ID of the doctor.
     * @return The DoctorDTO object representing the doctor.
     * @throws ResourceNotFoundException: If no doctor is found with the given ID.
     */
	public DoctorDTO findById(String id) throws Exception {
		logger.info("Finding one doctor");

		DoctorDTO doctor = repository.findById(UUID.fromString(id)).map(mapper::toDto)
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		return addDoctorSelfRel(doctor);

	}

    /**
     * Creates a new doctor.
     *
     * @param dto: The DoctorDTO object containing doctor information.
     * @return The created DoctorDTO object.
     * @throws BadRequestException: If the email provided already exists.
     */
	public DoctorDTO create(DoctorDTO dto) throws Exception {
		logger.info("Create one doctor");
		repository.findByEmail(dto.getEmail()).ifPresent(existingDoctor -> {
			throw new BadRequestException("Email " + MessageUtils.RECORDS_ALREADY_EXIST + ": " + dto.getEmail());
		});

		repository.findByCpf(dto.getCpf()).ifPresent(existingPatient -> {
			throw new BadRequestException("CPF " + MessageUtils.RECORDS_ALREADY_EXIST + ": " + dto.getCpf());
		});
		Doctor entity = mapper.toEntity(dto);
		return mapper.toDto(repository.save(entity));
	}

    /**
     * Updates an existing doctor.
     *
     * @param dto: The DoctorDTO object containing updated doctor information.
     * @return The updated DoctorDTO object.
     * @throws ResourceNotFoundException: If no doctor is found with the given ID.
     */
	public DoctorDTO update(DoctorDTO dto) throws Exception {
		logger.info("Update one doctor");

		Doctor entity = repository.findById(dto.getKey())
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		entity.setFullName(Objects.requireNonNullElse(dto.getFullName(), entity.getFullName()));
		entity.setEmail(Objects.requireNonNullElse(dto.getEmail(), entity.getEmail()));
		entity.setSpecialty(Objects.requireNonNullElse(dto.getSpecialty(), entity.getSpecialty()));
		entity.setCpf(Objects.requireNonNullElse(dto.getCpf(), entity.getCpf()));
		entity.setBirthDate(Objects.requireNonNullElse(DateUtil.convertStringToLocalDate(dto.getBirthDate()), entity.getBirthDate()));
		entity.setPhone(Objects.requireNonNullElse(dto.getPhone(), entity.getPhone()));

		DoctorDTO doctor = mapper.toDto(repository.save(entity));
		return addDoctorSelfRel(doctor);
	}

    /**
     * Deletes a doctor by their ID.
     *
     * @param id: The ID of the doctor to be deleted.
     * @throws ResourceNotFoundException: If no doctor is found with the given ID.
     */
	public void delete(String id) {
		logger.info("Deleting one doctor");
		Doctor entity = repository.findById(UUID.fromString(id))
				.orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NO_RECORDS_FOUND));
		repository.delete(entity);
	}

    /**
     *
     * @param doctor: The Doctor object containing doctor information
     * @return The DoctorDTO with self-rel link
     * @throws Exception
     */
	private DoctorDTO addDoctorSelfRel(DoctorDTO doctor) throws Exception {
	    return doctor.add(linkTo(methodOn(DoctorController.class).findById(doctor.getKey().toString())).withSelfRel());
	}
}
