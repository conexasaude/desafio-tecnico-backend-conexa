package br.com.conexa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.conexa.model.Consulta;
import br.com.conexa.model.Medico;
import br.com.conexa.services.AuthServices;
import br.com.conexa.services.ConsultaService;
import br.com.conexa.services.MedicoService;
import br.com.conexa.util.MediaType;
import br.com.conexa.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
public class MedicoController {

	@Autowired
	MedicoService medicoService;

	@Autowired
	ConsultaService consultaService;

	@Autowired
	AuthServices authServices;

	@PostMapping(value = "/signup", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	@Operation(summary = "Sign up", description = "Register one doctor", tags = { "Sign Up" })
	public Medico signUp(@RequestBody Medico medico) {

		return medicoService.signUp(medico);
	}

	@PostMapping(value = "/login")
	@Operation(summary = "Login!", description = "Login as a doctor", tags = { "Login" })
	public ResponseEntity logIn(@RequestBody LoginVO loginVO) {

		if (loginVO == null || loginVO.getEmail() == null || loginVO.getEmail().isBlank() || loginVO.getSenha() == null
				|| loginVO.getSenha().isBlank()) {

			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

		}

		ResponseEntity token = authServices.signin(loginVO);

		if (token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		}

		return token;
	}

	@PostMapping(value = "/attendance", consumes = { MediaType.APPLICATION_JSON }, produces = {
			MediaType.APPLICATION_JSON })
	@Operation(summary = "Attendance!", description = "Book a atendance", tags = { "Attendance" })
	public Consulta attendance(@RequestBody Consulta consulta) {

		return consultaService.bookAttendance(consulta);
	}

}
