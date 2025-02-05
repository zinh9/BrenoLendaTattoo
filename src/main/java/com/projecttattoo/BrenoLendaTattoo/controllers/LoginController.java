package com.projecttattoo.BrenoLendaTattoo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projecttattoo.BrenoLendaTattoo.dto.LoginDto;
import com.projecttattoo.BrenoLendaTattoo.services.ClienteService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/login")
    public String exibirFormularioLogin(Model model) {
		model.addAttribute("login", new LoginDto(null, null));
        return "login";
    }
	
	@PostMapping("/logar")
	public String processarLogin(
	    @ModelAttribute("login") LoginDto login, 
	    Model model,
	    HttpServletResponse response
	) {
	    ResponseEntity<Map<String, String>> authResponse = clienteService.login(login);

	    if (authResponse.getStatusCode().is2xxSuccessful()) {
	        String token = authResponse.getBody().get("token");
	        String role = authResponse.getBody().get("role");

	        // Armazena o token em um cookie
	        Cookie cookie = new Cookie("token", token);
	        cookie.setPath("/");
	        response.addCookie(cookie);

	        System.out.println("Login bem-sucedido. Role: " + role); // Log para depuração

	        if ("ADMIN".equals(role)) {
	            return "redirect:/catalogo/admin-catalogo";
	        } else {
	            return "redirect:/produto/catalogo"; 
	        }
	    } else {
	        model.addAttribute("error", "Credenciais inválidas");
	        System.out.println("Login falhou. Status: " + authResponse.getStatusCode()); // Log para depuração
	        return "login";
	    }
	}
}
