package com.projecttattoo.BrenoLendaTattoo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projecttattoo.BrenoLendaTattoo.dto.orcamento.RequestOrcamentoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.orcamento.ResponseOrcamentoDto;
import com.projecttattoo.BrenoLendaTattoo.models.Orcamento;
import com.projecttattoo.BrenoLendaTattoo.repositories.OrcamentoRespository;
import com.projecttattoo.BrenoLendaTattoo.services.OrcamentoService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/orcamentos")
public class OrcamentoController {
	
	@Autowired
	private OrcamentoService orcamentoService;
	
	@PostMapping("/novo-orcamento")
	public String register(@RequestBody RequestOrcamentoDto body, Model model) {
		orcamentoService.register(body);
		
		model.addAttribute("message", "Orçamento salvo com sucesso!");
		
		return "meus_orcamentos";
	}
	
	@GetMapping("/meus-orcamentos")
	public String getAll(Model model) {
		ResponseEntity<List<ResponseOrcamentoDto>> response = orcamentoService.getAll();		
		List<ResponseOrcamentoDto> orcamentos = response.getBody();
		model.addAttribute("orcamentos", orcamentos);
		return "meus_orcamentos";
	}
	
	@GetMapping("/{id}/editar")
	public String getOrcamentoById(@PathVariable Integer id, Model model) {
	    ResponseEntity<ResponseOrcamentoDto> response = orcamentoService.getById(id);
	    if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
	        model.addAttribute("orcamento", response.getBody());
	        return "novo_orcamento"; // Reutilizando o arquivo
	    }
	    return "redirect:/meus-orcamentos";
	}

	
	@PostMapping("/admin/orcamento/{id}/status")
	@PreAuthorize("hasRole('ADMIN')")
	public String updateStatus(@PathVariable Integer id, @RequestParam String newStatus) {
	    orcamentoService.updateStatus(id, newStatus);
	    return "redirect:/admin/orcamentos";
	}
	
	@PutMapping("/orcamentos/atualizar-orcamento/{id}")
	public String updateOrcamento(@PathVariable Integer id, @RequestBody RequestOrcamentoDto body) {
	    orcamentoService.update(id, body);
	    return "meus_orcamentos";
	}

}
