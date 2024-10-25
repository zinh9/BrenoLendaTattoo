package com.projecttattoo.BrenoLendaTattoo.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projecttattoo.BrenoLendaTattoo.dto.LoginDto;
import com.projecttattoo.BrenoLendaTattoo.dto.RequestRegisterDto;
import com.projecttattoo.BrenoLendaTattoo.dto.ResponseConfirmedDto;
import com.projecttattoo.BrenoLendaTattoo.dto.ResponseDto;
import com.projecttattoo.BrenoLendaTattoo.enums.Roles;
import com.projecttattoo.BrenoLendaTattoo.interfaces.UserInterfaceService;
import com.projecttattoo.BrenoLendaTattoo.models.Cliente;
import com.projecttattoo.BrenoLendaTattoo.models.Logins;
import com.projecttattoo.BrenoLendaTattoo.repositories.LoginsRepository;
import com.projecttattoo.BrenoLendaTattoo.repositories.RegisterRepository;

import br.com.caelum.stella.validation.CPFValidator;

@Service
public class UserService implements UserInterfaceService{
	
	@Autowired
	private RegisterRepository registerRepository;
	
	@Autowired
	private LoginsRepository loginsRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthenticatedService authenticatedService;
	
	private final JavaMailSender sender;
	
	public UserService(JavaMailSender sender) {
		this.sender = sender;
	}

	@Override
	public ResponseEntity<ResponseDto> register(RequestRegisterDto body) {
		Cliente cliente = registerRepository.findByEmail(body.email());
		
		if(cliente == null) {
			Cliente newCliente = new Cliente();
			newCliente.setCpf(body.cpf());
			if(!validateCpf(newCliente.getCpf())) {
				return ResponseEntity.status(412).build();
			}
			newCliente.setDataNascimento(body.dataNascimento());
			newCliente.setEmail(body.email());
			newCliente.setNome(body.nome());
			newCliente.setSobrenome(body.sobrenome());
			newCliente.setTelefone(body.telefone());
			newCliente.setSenha(encoder.encode(body.senha()));
			
			String code = sendEmail(body.email());
			
			Logins logins = new Logins();
			
			logins.setEmail(newCliente.getEmail());
			logins.setNome(newCliente.getNome());
			logins.setSenha(newCliente.getSenha());
			logins.setVerificationCode(code);
			logins.setUserRole(Roles.USER);
			logins.setVerifiedAccount(false);
			
			loginsRepository.save(logins);
			registerRepository.save(newCliente);
			
			return ResponseEntity.ok().body(new ResponseDto(newCliente.getId(),newCliente.getEmail(),newCliente.getSenha()));
		}
		
		return ResponseEntity.badRequest().build();
	}

	@Override
	public ResponseEntity<String> login(LoginDto body) {
		Logins cliente = loginsRepository.findByEmail(body.email());
		boolean isVerified = getVerificationAccount(cliente);
		
		if(cliente != null) {
			var clienteAuthenticationToken = new UsernamePasswordAuthenticationToken(body.email(), body.senha());
			authenticationManager.authenticate(clienteAuthenticationToken);
			String token = authenticatedService.getToken(body);
			cliente.setToken(token);
			loginsRepository.save(cliente);
			
			return ResponseEntity.ok().body(token);
		}
		
		return ResponseEntity.badRequest().build();
	}

	@Override
	public boolean validateCpf(String cpf) {
		CPFValidator cpfValidator = new CPFValidator();
		
		try {
			cpfValidator.assertValid(cpf);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String sendEmail(String to) {
		var message = new SimpleMailMessage();
		Random random = new Random();
		message.setTo(to);
		message.setText("CÓDIGO DE \nSEGURANÇA \n\nUse o código de segurança para confirmar sua conta \n\nclique no link abaixo \n\nhttp://localhost:8080/confirm \n\ncódigo de segurança: "+ random.nextInt(1000,9999));
		sender.send(message);
		
		return message.getText().replace("CÓDIGO DE \nSEGURANÇA \n\nUse o código de segurança para confirmar sua conta \n\nclique no link abaixo \n\nhttp://localhost:8080/confirm \n\ncódigo de segurança: ", "").trim();
	}

	@Override
	public ResponseEntity<ResponseConfirmedDto> confirmEmail(String code) {
		Logins clienteCode = loginsRepository.findByVerificationCode(code);
		
		if(clienteCode != null) {
			clienteCode.setVerifiedAccount(true);
			loginsRepository.save(clienteCode);
			
			return ResponseEntity.ok().body(new ResponseConfirmedDto("Account confirmed"));
		}
		
		return ResponseEntity.badRequest().body(new ResponseConfirmedDto("Code is not found"));
	}

	@Override
	public String redifinedPassword(String body) {
		Logins cliente = loginsRepository.findByEmail(body);
		
		if(cliente != null) {
			String code = sendEmail(body);
			loginsRepository.save(cliente);
			
			return code;
		}
		
		return null;
	}

	@Override
	public ResponseEntity<String> resetPassword(String email, LoginDto senha) {
		Cliente cliente = registerRepository.findByEmail(email);
		
		if(cliente != null) {
			cliente.setSenha(senha.senha());
			
			return ResponseEntity.ok().body(cliente.getSenha());
		}
		
		return ResponseEntity.badRequest().build();
	}

	@Override
	public void sendLink(String to, String msg) {
		var message = new SimpleMailMessage();
		message.setTo(to);
		message.setText(msg);
		sender.send(message);
	}

	@Override
	public ResponseEntity<String> getClientes(String email) {
		Logins cliente = loginsRepository.findByEmail(email);
		
		if(cliente != null) {
			return ResponseEntity.ok().body(cliente.getNome());
		}
		
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<String> verifyCode(String code) {
		Logins clienteLogin = loginsRepository.findByVerificationCode(code);
		
		if(clienteLogin != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("código aberto");
		}
		
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}

	@Override
	public boolean getVerificationAccount(Logins email) {
		Logins cliente = loginsRepository.findByEmail(email.getEmail());
		
		if(cliente != null) {
			if(cliente.isVerifiedAccount()) {
				return true;
			}
			
			return false;
		}
		
		return false;
	}

}
