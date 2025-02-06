package com.projecttattoo.BrenoLendaTattoo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSegurityConfig {
	@Autowired
	private com.projecttattoo.BrenoLendaTattoo.filters.Filter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/auth/login", "/auth/logar", "/cliente/cadastro", "/cliente/register",
								"/produto/catalogo", "/static/**", "/css/**", "/js/**", "/images/**", "/uploads/**").permitAll() // Rotas públicas
						.requestMatchers("/orcamentos/meus-orcamentos").hasRole("USER")
						.requestMatchers("/orcamentos/historico").hasRole("USER")
						.requestMatchers("/orcamentos/novo").hasRole("USER")
						.requestMatchers("/orcamentos/{id}/editar").hasRole("USER")
						.requestMatchers("/orcamentos/{id}/editar-orcamento").hasRole("USER")
						.requestMatchers("/orcamentos/{id}/deletar").hasRole("USER")
						.requestMatchers("/produto/admin-catalogo").hasRole("ADMIN")
						.requestMatchers("/produto/admin-novo-produto").hasRole("ADMIN")
						.requestMatchers("/produto/register").hasRole("ADMIN").requestMatchers("/produto/{id}/deletar")
						.hasRole("ADMIN").requestMatchers("/orcamentos/admin-orcamentos").hasAnyRole("ADMIN")
						.requestMatchers("/produto/{id}/concluir").hasAnyRole("ADMIN")
						.anyRequest().authenticated() // Demais rotas exigem autenticação
				).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
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
