package com.felipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.felipe.model.User;
import com.felipe.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping
	public List<User> findAllId() throws Exception {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public User findById(@PathVariable(value = "id") String id) throws Exception {
		return service.findById(id);
	}
	
	@PostMapping
	public User create(@RequestBody User user) throws Exception {
		return service.create(user);
	}
	
	@PutMapping
	public User update(@RequestBody User user) throws Exception {
		return service.update(user);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") String id) throws Exception {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
