package com.projecttattoo.BrenoLendaTattoo.interfaces;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;

import com.projecttattoo.BrenoLendaTattoo.dto.ClienteDto;
import com.projecttattoo.BrenoLendaTattoo.dto.LoginDto;
import com.projecttattoo.BrenoLendaTattoo.dto.RequestRegisterDto;
import com.projecttattoo.BrenoLendaTattoo.dto.ResponseConfirmedDto;
import com.projecttattoo.BrenoLendaTattoo.dto.ResponseDto;
import com.projecttattoo.BrenoLendaTattoo.dto.UpdateClienteDto;
import com.projecttattoo.BrenoLendaTattoo.models.Logins;

public interface ClienteInterfaceService {
	public ResponseEntity<ResponseDto> register(RequestRegisterDto body);
	public ResponseEntity<String> login(LoginDto body);
	public ResponseEntity<Integer> delete(Integer id);
	public ResponseEntity<UpdateClienteDto> update(int id, UpdateClienteDto body);
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