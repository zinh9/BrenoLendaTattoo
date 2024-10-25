package com.projecttattoo.BrenoLendaTattoo.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.projecttattoo.BrenoLendaTattoo.models.Logins;
import com.projecttattoo.BrenoLendaTattoo.repositories.LoginsRepository;
import com.projecttattoo.BrenoLendaTattoo.services.AuthenticatedService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Filter extends OncePerRequestFilter{
	
	@Autowired
	private AuthenticatedService authenticatedService;
	
	@Autowired
	private LoginsRepository registerRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = extraiTokenHeader(request);
		
		if(token != null) {
			String login = authenticatedService.validateToken(token);
			Logins cliente = registerRepository.findByEmail(login);
			var authentication = new UsernamePasswordAuthenticationToken(cliente, token, cliente.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}
	
	public String extraiTokenHeader(HttpServletRequest request) {
		var header = request.getHeader("Authorization");
		
		if(header == null) {
			return null;
		}
		
		return header.replace("Bearer", "").trim();
	}
}
