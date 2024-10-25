package com.projecttattoo.BrenoLendaTattoo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projecttattoo.BrenoLendaTattoo.dto.LoginDto;
import com.projecttattoo.BrenoLendaTattoo.dto.RequestRegisterDto;
import com.projecttattoo.BrenoLendaTattoo.dto.ResponseConfirmedDto;
import com.projecttattoo.BrenoLendaTattoo.dto.ResponseDto;
import com.projecttattoo.BrenoLendaTattoo.services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cliente")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDto body){
		return userService.login(body);
	}
	
	@PostMapping("/register")
	public ResponseEntity<ResponseDto> register(@RequestBody RequestRegisterDto body){
		return userService.register(body);
	}
	
	@PostMapping("/confirm")
	public ResponseEntity<ResponseConfirmedDto> confirmEmail(@RequestParam(name = "code") String code){
		return userService.confirmEmail(code);
	}
}
