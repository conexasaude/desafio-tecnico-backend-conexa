package com.felipe.unittests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
//@ExtendWith(MockitoExtension.class)
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
