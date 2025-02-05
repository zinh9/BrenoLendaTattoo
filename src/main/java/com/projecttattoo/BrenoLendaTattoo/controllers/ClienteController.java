package com.projecttattoo.BrenoLendaTattoo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projecttattoo.BrenoLendaTattoo.dto.ResponseDto;
import com.projecttattoo.BrenoLendaTattoo.dto.cliente.ClienteDto;
import com.projecttattoo.BrenoLendaTattoo.dto.cliente.RequestRegisterDto;
import com.projecttattoo.BrenoLendaTattoo.services.ClienteService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/cadastro")
	public String exibirTeladCadastro(Model model) {
		model.addAttribute("cliente", new RequestRegisterDto(null, null, null, null, null));
		return "cadastro";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute("cliente") RequestRegisterDto request, Model model) {
	    ResponseEntity<ResponseDto> response = clienteService.register(request);

	    if (response.getStatusCode().is2xxSuccessful()) {
	        model.addAttribute("message", "Cliente salvo com sucesso!");
	    } else {
	        model.addAttribute("error", "Erro ao salvar o cliente.");
	    }

	    return "redirect:/auth/login";
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
	public ResponseEntity<RequestRegisterDto> update(@PathVariable("id") Integer id, @RequestBody RequestRegisterDto body){
		return clienteService.update(id, body);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Integer id){
		return clienteService.delete(id);
	}
}
