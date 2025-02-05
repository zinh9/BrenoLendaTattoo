package com.projecttattoo.BrenoLendaTattoo.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.projecttattoo.BrenoLendaTattoo.dto.LoginDto;
import com.projecttattoo.BrenoLendaTattoo.dto.ResponseConfirmedDto;
import com.projecttattoo.BrenoLendaTattoo.dto.ResponseDto;
import com.projecttattoo.BrenoLendaTattoo.dto.cliente.ClienteDto;
import com.projecttattoo.BrenoLendaTattoo.dto.cliente.RequestRegisterDto;
import com.projecttattoo.BrenoLendaTattoo.models.Logins;

public interface ClienteInterfaceService {
	public ResponseEntity<ResponseDto> register(RequestRegisterDto body);
	public ResponseEntity<Map<String, String>> login(LoginDto body);
	public ResponseEntity<Integer> delete(Integer id);
	public ResponseEntity<RequestRegisterDto> update(int id, RequestRegisterDto body);
	public ResponseEntity<List<ClienteDto>> getAll();
	public ResponseEntity<ClienteDto> getById(Integer id);
	//public boolean validateCpf(String cpf);
	public ResponseEntity<ResponseConfirmedDto> confirmEmail(String code);
	public ResponseEntity<ResponseConfirmedDto> redifinedPassword(String body);
	public ResponseEntity<String> resetPassword(String email, LoginDto senha);
	public ResponseEntity<String> getClientes(String email);
	public ResponseEntity<String> verifyCode(String code);
	public boolean getVerificationAccount(Logins email);
}
