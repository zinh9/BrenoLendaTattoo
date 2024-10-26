package com.projecttattoo.BrenoLendaTattoo.segurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ch.qos.logback.core.filter.Filter;

@Configuration
@EnableWebSecurity
public class WebSegurityConfig {
	@Autowired
	private jakarta.servlet.Filter filter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http
				.csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.GET, "/cliente").permitAll()
						.requestMatchers(HttpMethod.POST, "/clinte/register").permitAll()
						.requestMatchers(HttpMethod.POST, "/cliente/login").permitAll()
						.requestMatchers(HttpMethod.GET, "/cliente/confirm").permitAll()
						.requestMatchers(HttpMethod.GET, "/cliente/redifine").permitAll()
						.requestMatchers(HttpMethod.GET, "/cliente/email").permitAll()
						.requestMatchers(HttpMethod.PUT, "/cliente/reset").permitAll()
						.requestMatchers(HttpMethod.GET, "/cliente/verify").permitAll()
						.requestMatchers(HttpMethod.GET, "/cliente/get-token").permitAll()
						.anyRequest()
						.authenticated()
						)
				//.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticate) throws Exception {
		return authenticate.getAuthenticationManager();
	}
}
