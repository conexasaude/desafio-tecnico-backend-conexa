package com.felipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.felipe.model.User;
import com.felipe.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@RequestMapping(method = RequestMethod.GET)
	public List<User> findAllId() throws Exception {
		return service.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User findById(@PathVariable(value = "id") String id) throws Exception {
		return service.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public User create(@RequestBody User user) throws Exception {
		return service.create(user);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public User update(@RequestBody User user) throws Exception {
		return service.create(user);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable(value = "id") String id) throws Exception {
		service.delete(id);
	}
	
}
