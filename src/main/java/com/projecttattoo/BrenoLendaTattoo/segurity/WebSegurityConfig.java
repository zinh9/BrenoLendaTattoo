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
	private com.projecttattoo.BrenoLendaTattoo.filters.Filter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/auth").permitAll()
						.requestMatchers( "/cliente").permitAll()
						.requestMatchers("/produto").permitAll()
						.requestMatchers(HttpMethod.GET, "/cliente/**").permitAll()
						.requestMatchers(HttpMethod.DELETE, "/cliente/**").permitAll()
						.requestMatchers(HttpMethod.PUT, "/cliente/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/cliente/register").permitAll()
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/produto/register").permitAll()
						.requestMatchers(HttpMethod.GET, "/produto/**").permitAll()
						.requestMatchers(HttpMethod.PUT, "/produto/**").permitAll()
						.requestMatchers(HttpMethod.DELETE, "/produto/**").permitAll()
						.anyRequest()
						.authenticated())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
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
