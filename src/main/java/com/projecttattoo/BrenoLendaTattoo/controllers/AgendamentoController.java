package com.projecttattoo.BrenoLendaTattoo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.RequestAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.RequestUpdateAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.ResponseAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.services.AgendamentoService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/agendamento")
public class AgendamentoController {
	
	@Autowired
	private AgendamentoService agendamentoService;
	
	@GetMapping
	public ResponseEntity<List<ResponseAgendamentoDto>> getAll(){
		return agendamentoService.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseAgendamentoDto> getById(@PathVariable("id") Integer id){
		return agendamentoService.getById(id);
	}
	
	@PostMapping("/agendar")
	public ResponseEntity<ResponseAgendamentoDto> register(@RequestBody RequestAgendamentoDto body){
		body.dataAgendamento();
		body.horario();
		return agendamentoService.register(body);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseAgendamentoDto> update(@PathVariable("id") Integer id, @RequestBody RequestUpdateAgendamentoDto body){
		return agendamentoService.update(id, body);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Integer id){
		return agendamentoService.delete(id);
	}
}
