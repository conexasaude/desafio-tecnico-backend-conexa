package com.felipe.unittests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.felipe.exceptions.BadRequestException;
import com.felipe.mapper.UserMapper;
import com.felipe.model.User;
import com.felipe.model.dto.v1.UserDTO;
import com.felipe.repositories.UserRepository;
import com.felipe.service.UserService;
import com.felipe.unittests.mapper.mocks.MockUser;
import com.felipe.util.MessageUtils;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Mock
	private UserRepository repository;

//	@Autowired
	private UserMapper mapper = new UserMapper();

	@Autowired
	@InjectMocks
	private UserService service;

	private UserDTO userDto;

	private User user;

	MockUser inputObject = new MockUser();;

	@BeforeEach
	public void setUp() {
		user = inputObject.mockRandomEntity(true);
		userDto = mapper.toDto(user);
	}

	@DisplayName("JUnit test for Given Users Object when Save User then Return User Object")
	@Test
	void testGivenUserObject_whenSaveUser_thenReturnUserObject() throws Exception {
		logger.info("JUnit test for Given Users Object when Save User then Return User Object");
		
		given(repository.findByEmail(anyString())).willReturn(Optional.empty());
		given(repository.save(user)).willReturn(user);

		logger.info(user.toString());
		logger.info("DTO => " + userDto.toString());

		UserDTO userDtoCreated = service.create(userDto);
		logger.info("CREATED => " + userDtoCreated.toString());

		assertNotNull(userDtoCreated);
		assertTrue(!userDtoCreated.getKey().toString().isEmpty());
		assertEquals(user.getFullName(), userDtoCreated.getFullName());

	}

	@DisplayName("JUnit test for Given Existing Email when Save User then Throws Exception")
	@Test
	void testGivenExistingEmail_whenSaveUser_thenThrowsException() throws Exception {
		logger.info("JUnit test for Given Existing Emai when Save User then Throws Exception");

		// Set the email of the UserDTO to "songpagaciv.2961@example.com"
		userDto.setEmail("songpagaciv.2961@example.com");

		// Mock the behavior of the repository
		given(repository.findByEmail("songpagaciv.2961@example.com")).willReturn(Optional.of(user));

		BadRequestException exception = assertThrows(BadRequestException.class, () -> {
			logger.info("Before service.create");
			service.create(userDto);
			logger.info("After service.create");
		});

		assertEquals("Email " + MessageUtils.RECORDS_ALREADY_EXIST + ": " + userDto.getEmail(), exception.getMessage());
		verify(repository, never()).save(any(User.class));
	}
	
	@DisplayName("JUnit test for Given Users List when Find All Users Then Return Users List")
	@Test
	void testGivenUsersList_whenFindAllUsers_thenReturnUsersList()  {
		logger.info("JUnit test for Given Users List when Find All Users Then Return Users List");

		// Mock the behavior of the repository
		given(repository.findAll()).willReturn(inputObject.mockRandomEntityList(3, true));

		List<UserDTO> usersDtoList = service.findAll();
		logger.info("SIZE => " + usersDtoList.size());

		assertNotNull(usersDtoList);
		assertEquals(3, usersDtoList.size());
	}
	
	@DisplayName("JUnit test for Given Empty Users List when Find All Users Then Return Empty Users List")
	@Test
	void testGivenEmptyUsersList_whenFindAllUsers_thenReturnEmptyUsersList() throws Exception {
		logger.info("JUnit test for Given Empty Users List when Find All Users Then Return Empty Users List");

		// Mock the behavior of the repository
		given(repository.findAll()).willReturn(Collections.emptyList());

		List<UserDTO> usersDtoList = service.findAll();
		logger.info("SIZE => " + usersDtoList.size());

		assertTrue(usersDtoList.isEmpty());
		assertEquals(0, usersDtoList.size());
	}

	@DisplayName("JUnit test for Given UserId when Find By Id Then Return User Object")
	@Test
	void testGivenUsersId_whenFindById_thenReturnUserObject() throws Exception {
		logger.info("JUnit test for Given UserId when Find By Id Then Return User Object");

	    //UUID valid for mocks
	    String userIdString = UUID.randomUUID().toString();
		
		given(repository.findById(any(UUID.class))).willReturn(Optional.of(user));


		UserDTO userFound = service.findById(userIdString);
		logger.info("FoundByID => " + userDto.toString());
		logger.info("USER => " + user.toString());

		assertNotNull(userFound);
		assertTrue(!userFound.getKey().toString().isEmpty());
		assertEquals(user.getFullName(), userFound.getFullName());
	}
	
	@DisplayName("JUnit test for Given User Object when Update User Then Return Update User Object")
	@Test
	void testGivenUserObject_whenUpdateUser_thenReturnUpdateUserObject() throws Exception {
		logger.info("JUnit test for Given Object when Update User Then Return Update User Object");

		given(repository.findById(any(UUID.class))).willReturn(Optional.of(user));
		given(repository.save(any(User.class))).willReturn(user);

		String newName = "Mauricio Di Paula";
		String newEmail = "mauricio_dipaula@email.com";
		logger.info("Before Update => " + userDto.toString());

		user.setFullName(newName);
		user.setEmail(newEmail);
		userDto = mapper.toDto(user);

		UserDTO userUpdated = service.update(userDto);
		logger.info("After Update => " + userDto.toString());

		assertNotNull(userUpdated);
		assertEquals(newName, userUpdated.getFullName());
		assertEquals(newEmail, userUpdated.getEmail());
	}
	
	@DisplayName("JUnit test for Given UserID when Delete User then do Nothing")
	@Test
	void testGivenUserID_whenDeleteUser_thenDoNothing() throws Exception {
		logger.info("JUnit test for Given UserID when Delete User then do Nothing");

		given(repository.findById(any(UUID.class))).willReturn(Optional.of(user));
		willDoNothing().given(repository).delete(user);

		service.delete(user.getId().toString());
		
		verify(repository, times(1)).delete(user);
	}

}
