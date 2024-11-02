package com.projecttattoo.BrenoLendaTattoo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.RequestAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.ResponseAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.services.AgendamentoService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/agendamento")
public class AgendamentoController {
	
	@Autowired
	private AgendamentoService agendamentoService;
	
	@PostMapping("/agendar")
	public ResponseEntity<ResponseAgendamentoDto> register(@RequestBody RequestAgendamentoDto body){
		return agendamentoService.register(body);
	}
}
