package com.felipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipe.model.dto.v1.PasswordUpdateDTO;
import com.felipe.model.dto.v1.UserDTO;
import com.felipe.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAllId() throws Exception {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable(value = "id") String id) throws Exception {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);

	}

	@PostMapping
	public UserDTO create(@RequestBody UserDTO userDTO) throws Exception {
		return service.create(userDTO);
	}

	@PutMapping
	public UserDTO update(@RequestBody UserDTO userDTO) throws Exception {
		return service.update(userDTO);
	}

	@PatchMapping("/{id}/password")
	public ResponseEntity<Void> updatePassword(@PathVariable(value = "id") String id,
			@RequestBody PasswordUpdateDTO passwordUpdateDTO) {
		service.changePassword(id, passwordUpdateDTO);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") String id) throws Exception {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
