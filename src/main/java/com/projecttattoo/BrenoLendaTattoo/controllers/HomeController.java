package com.projecttattoo.BrenoLendaTattoo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/home")
public class HomeController {
	
	@GetMapping
	public String indexLeadPage() {
		return "leadpage";
	}
}
