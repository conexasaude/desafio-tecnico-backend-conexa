package com.felipe.unittests.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.felipe.mapper.UserMapper;
import com.felipe.model.User;
import com.felipe.repositories.UserRepository;
import com.felipe.service.UserService;
import com.felipe.unittests.mapper.mocks.MockUser;

//@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	MockUser input;

	@Mock
	private UserRepository repository;

	@Mock
	private UserMapper mapper;

	@InjectMocks
	private UserService service;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockUser();
		MockitoAnnotations.openMocks(this);
	}

    @DisplayName("JUnit test for Given Empty Users List when findAll Users then Return Empty Users List")
	@Test
	void testFindAll() {
        when(repository.findAll()).thenReturn(List.of());
        given(repository.findAll()).willReturn(Collections.emptyList());
		var result = service.findAll();
		System.out.println(result.toString());

	}

	@DisplayName("JUnit test for findById method")
	@Test
	void testFindById() throws Exception {
		UUID userId = UUID.fromString("0c38ae25-0d29-43ad-91d4-73e91309267b");

		User user = input.mockEntity();
		user.setId(userId);
		System.out.println(user.toString());

		// Configurar o mock do repositório para retornar o Optional contendo o usuário
		// mockado
		when(repository.findById(userId)).thenReturn(Optional.of(user));

		// Executar o método que está sendo testado
		var result = service.findById("0c38ae25-0d29-43ad-91d4-73e91309267b");

		// Verificar o resultado do teste
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		System.out.println(result.toString());
	}

	@Test
	void testCreate() {
		fail("Not yet implemented");
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
