package com.felipe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.felipe.model.User;
import com.felipe.util.DateUtil;

@Service
public class UserService {
	private Logger logger = Logger.getLogger(UserService.class.getName());
	
	public List<User> findAll() {
		logger.info("Finding All User");

		List<User> users = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			User user = mockUser(i);
			users.add(user);
		}
		return users;
	}
	

	public User findById(String id) {
		logger.info("Finding one user");
		User user = new User();
		user.setFullname("Givonaldo Pereira");
		user.setEmail("givonaldo_pereira@gmail.com");
		user.setPassword("?medico@Givon");
		user.setSpecialty("Cardiologista");
		user.setCpf("101.202.303-11");
		user.setBirthDate(DateUtil.convertStringToLocalDate("10/03/1980"));
		user.setPhone("(21) 3232-6565");
		return user;
	}
	
	public User create(User user) {
		logger.info("Create one user");
		
		return user;
	}
	
	public User update(User user) {
		logger.info("Update one user");
		
		return user;
	}
	
	public void delete(String id) {
		logger.info("Deleting one user");
	}
	
	private User mockUser(int i) {
		User user = new User();
		user.setFullname("Givonaldo Pereira " + i);
		user.setEmail("givonaldo_pereira"+ i + "@gmail.com");
		user.setPassword("?medico@Givon");
		user.setSpecialty("Cardiologista");
		user.setCpf("101.202.303-1" + i);
		user.setBirthDate(DateUtil.convertStringToLocalDate("10/03/1980"));
		user.setPhone("(21) 3232-656" + i);
		return user;
	}

}
