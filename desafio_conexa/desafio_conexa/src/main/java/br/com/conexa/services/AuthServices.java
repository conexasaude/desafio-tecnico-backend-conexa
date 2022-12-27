package br.com.conexa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import br.com.conexa.exceptions.WrongPasswordException;
import br.com.conexa.model.Medico;
import br.com.conexa.repositories.MedicoRepository;
import br.com.conexa.security.JwtTokenProvider;
import br.com.conexa.vo.LoginVO;
import br.com.conexa.vo.TokenVO;

@Service
public class AuthServices {


	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MedicoRepository repository;
	
	public ResponseEntity signin(LoginVO loginVO)
	{
		try {
			
			String email = loginVO.getEmail();
			String senha = loginVO.getSenha();
			
			
			Medico medico = repository.findByEmail(email);
			
			
			
			TokenVO tokenResponse = new TokenVO();
			
			if (medico != null) {
				
				if(BCrypt.checkpw(senha, medico.getSenha()))
				{
					tokenResponse = tokenProvider.createAccessToken(email);
				}
				else
				{
					throw new WrongPasswordException("Wrong password!");
				}
			}
			else
			{
				throw new UsernameNotFoundException("Email" + email+" not found !");
			}
			
			return ResponseEntity.ok(tokenResponse);
			
		} catch (Exception e) {
			
			throw new BadCredentialsException("Invalid email/password supplied");
		}
	}
	
}
