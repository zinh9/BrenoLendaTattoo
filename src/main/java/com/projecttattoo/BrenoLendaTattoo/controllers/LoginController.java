package com.projecttattoo.BrenoLendaTattoo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projecttattoo.BrenoLendaTattoo.dto.LoginDto;
import com.projecttattoo.BrenoLendaTattoo.dto.ResponseDto;
import com.projecttattoo.BrenoLendaTattoo.dto.cliente.RequestRegisterDto;
import com.projecttattoo.BrenoLendaTattoo.services.ClienteService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
    public String home() {
        return "form-cadastro";
    }
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDto body){
		return clienteService.login(body);
	}
}
