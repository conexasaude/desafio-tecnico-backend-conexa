package com.juliagomes.desafiobackendconexa.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juliagomes.desafiobackendconexa.model.BlackListToken;
import com.juliagomes.desafiobackendconexa.repository.BlackListRepository;
import com.juliagomes.desafiobackendconexa.security.JwtTokenProvider;

@Service
public class InvalidaJWTLogout {
	
	@Autowired
	private BlackListRepository blackListRepository;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	
	public void addTokenBlackList(String authorization) {
		String token = authorization.substring(7);
		String username = jwtTokenProvider.getUsernameFromJWT(token);
		BlackListToken blackListToken = new BlackListToken();
		blackListToken.setToken(token);
		blackListToken.setUsername(username);
		blackListToken.setDate(LocalDate.now());
		blackListRepository.save(blackListToken);
		blackListRepository.deleteExpiredTokens();
	}

}
