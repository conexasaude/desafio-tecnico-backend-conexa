package com.felipe.unittests.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.felipe.mapper.UserMapper;
import com.felipe.model.User;
import com.felipe.model.dto.v1.UserDTO;
import com.felipe.repositories.UserRepository;
import com.felipe.service.UserService;
import com.felipe.unittests.mapper.mocks.MockUser;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Mock
	private UserRepository repository;

//	@Autowired
	private UserMapper mapper = new UserMapper();
	
	@InjectMocks
	@Autowired
	private UserService service;

	private UserDTO userDto;

	private User user;
	
	MockUser inputObject = new MockUser();;

	@BeforeEach
	public void setUp() {
		user = inputObject.mockRandomEntity();
		userDto = mapper.toDto(user);
	}
	

//	@BeforeEach
//	void setUpMocks() throws Exception {
//		MockitoAnnotations.openMocks(this);
//	}

//    @DisplayName("JUnit test for Given Empty Users List when findAll Users then Return Empty Users List")
//	@Test
//	void testFindAll() {
//        when(repository.findAll()).thenReturn(List.of());
//        given(repository.findAll()).willReturn(Collections.emptyList());
//		var result = service.findAll();
//		System.out.println(result.toString());
//
//	}
//
//	@DisplayName("JUnit test for findById method")
//	@Test
//	void testFindById() throws Exception {
//		UUID userId = UUID.fromString("0c38ae25-0d29-43ad-91d4-73e91309267b");
//
//		user.setId(userId);
//		System.out.println(user.toString());
//
//		// Configurar o mock do repositório para retornar o Optional contendo o usuário
//		// mockado
//		when(repository.findById(userId)).thenReturn(Optional.of(user));
//
//		// Executar o método que está sendo testado
//		var result = service.findById("0c38ae25-0d29-43ad-91d4-73e91309267b");
//
//		// Verificar o resultado do teste
//		assertNotNull(result);
//		assertNotNull(result.getKey());
//		assertNotNull(result.getLinks());
//		System.out.println(result.toString());
//	}

	@DisplayName("JUnit test for Given Users Object when Save User then Return User Object")
	@Test
	void testCreate() throws Exception {
		logger.info("JUnit test for Given Users Object when Save User then Return User Object");
		
		given(repository.findByEmail("example@email.com")).willReturn(Optional.empty());
		given(repository.save(user)).willReturn(user);
		
		logger.info(user.toString());
		logger.info("DTO => " + userDto.toString());

		UserDTO userDtoCreated = service.create(userDto);
		logger.info("CREATED => " + userDtoCreated.toString());

		assertNotNull(userDtoCreated);
		assertTrue(!userDtoCreated.getKey().toString().isEmpty());
        assertEquals(user.getFullName(), userDtoCreated.getFullName());

	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testChangePassword() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

}
