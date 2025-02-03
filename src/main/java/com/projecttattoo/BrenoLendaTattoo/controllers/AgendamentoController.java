package com.projecttattoo.BrenoLendaTattoo.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.RequestAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.ResponseAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.models.Orcamento;
import com.projecttattoo.BrenoLendaTattoo.repositories.OrcamentoRespository;
import com.projecttattoo.BrenoLendaTattoo.services.AgendamentoService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/agendamento")
public class AgendamentoController {
	
	@Autowired
	private AgendamentoService agendamentoService;
	
	@Autowired
	private OrcamentoRespository orcamentoRespository;
	
	@GetMapping("/{id}/novo-agendamento")
	public String agendarOcamento(@PathVariable Integer id, Model model) {
		Orcamento orcamento = orcamentoRespository.getById(id);
		
		model.addAttribute("orcamento", orcamento);
		model.addAttribute("agendamento", new ResponseAgendamentoDto(null, null, null, null));
		return "admin_agendamento";
	}
	
	@PostMapping("/{id}/novo")
	public String register(
			@RequestParam LocalDate dataAgendamento,
			@RequestParam LocalTime horario,
			@PathVariable Integer id,
			Model model
			){
		RequestAgendamentoDto request = new RequestAgendamentoDto(dataAgendamento, horario, id);
		ResponseEntity<ResponseAgendamentoDto> response = agendamentoService.register(request);
		
		if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Agendamento salvo com sucesso!");
        } else {
            model.addAttribute("error", "Erro ao salvar o agendamento.");
        }
		
        return "redirect:/orcamentos/admin-orcamentos";
	}
}
