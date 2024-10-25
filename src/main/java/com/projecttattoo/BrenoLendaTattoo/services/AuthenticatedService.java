package com.projecttattoo.BrenoLendaTattoo.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.projecttattoo.BrenoLendaTattoo.dto.LoginDto;
import com.projecttattoo.BrenoLendaTattoo.models.Logins;
import com.projecttattoo.BrenoLendaTattoo.repositories.LoginsRepository;

@Service
public class AuthenticatedService implements UserDetailsService{
	@Autowired
	private LoginsRepository repository;
	
	@Value("key.auth.token")
	private String key;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Logins cliente = repository.findByEmail(email);
		
		if(cliente == null) {
			throw new UsernameNotFoundException("Cliente não encontrado com o email: " + email);
		}
		
		return cliente;
	}
	
	public String getToken(LoginDto loginDto) {
		Logins cliente = repository.findByEmail(loginDto.email());
		
		return setToken(cliente);
	}
	
	public String setToken(Logins cliente) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(key);
			String token = JWT.create()
					.withIssuer("integration-nextjs")
					.withSubject(cliente.getEmail())
					.withExpiresAt(getDateExpirated())
					.sign(algorithm);
			return token;
		} catch (JWTCreationException e) {
			throw new RuntimeException("Error to the create token "+e.getMessage());		}
	}
	
	public Instant getDateExpirated() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(key);
			return JWT.require(algorithm).withIssuer("integration-nextjs")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTCreationException e) {
			return "";
		}
	}
}
