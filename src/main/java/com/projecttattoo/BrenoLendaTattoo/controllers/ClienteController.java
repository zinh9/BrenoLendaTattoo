package com.projecttattoo.BrenoLendaTattoo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projecttattoo.BrenoLendaTattoo.dto.ResponseDto;
import com.projecttattoo.BrenoLendaTattoo.dto.cliente.ClienteDto;
import com.projecttattoo.BrenoLendaTattoo.dto.cliente.RequestRegisterDto;
import com.projecttattoo.BrenoLendaTattoo.dto.cliente.UpdateClienteDto;
import com.projecttattoo.BrenoLendaTattoo.services.ClienteService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@PostMapping("/register")
	public ResponseEntity<ResponseDto> register(@RequestBody RequestRegisterDto body){
		return clienteService.register(body);
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDto>> getAll(){
		return clienteService.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDto> getById(@PathVariable("id") Integer id){
		return clienteService.getById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UpdateClienteDto> update(@PathVariable("id") Integer id, @RequestBody UpdateClienteDto body){
		return clienteService.update(id, body);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Integer id){
		return clienteService.delete(id);
	}
}
