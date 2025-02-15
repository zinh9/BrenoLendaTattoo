package com.projecttattoo.BrenoLendaTattoo.interfaces;

import org.springframework.http.ResponseEntity;

import com.projecttattoo.BrenoLendaTattoo.dto.LoginDto;
import com.projecttattoo.BrenoLendaTattoo.dto.RequestRegisterDto;
import com.projecttattoo.BrenoLendaTattoo.dto.ResponseConfirmedDto;
import com.projecttattoo.BrenoLendaTattoo.dto.ResponseDto;
import com.projecttattoo.BrenoLendaTattoo.models.Logins;

public interface UserInterfaceService {
	public ResponseEntity<ResponseDto> register(RequestRegisterDto body);
	public ResponseEntity<String> login(LoginDto body);
	public boolean validateCpf(String cpf);
	public ResponseEntity<ResponseConfirmedDto> confirmEmail(String code);
	public ResponseEntity<ResponseConfirmedDto> redifinedPassword(String body);
	public ResponseEntity<String> resetPassword(String email, LoginDto senha);
	public ResponseEntity<String> getClientes(String email);
	public ResponseEntity<String> verifyCode(String code);
	public boolean getVerificationAccount(Logins email);
}
