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

import com.projecttattoo.BrenoLendaTattoo.dto.produto.RequestProdutoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.produto.ResponseProdutoDto;
import com.projecttattoo.BrenoLendaTattoo.services.ProdutoService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@PostMapping("/register")
	public ResponseEntity<ResponseProdutoDto> register(@RequestBody RequestProdutoDto body){
		return produtoService.register(body);
	}
	
	@GetMapping
	public ResponseEntity<List<ResponseProdutoDto>> getAll(){
		return produtoService.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseProdutoDto> getById(@PathVariable("id") Integer id){
		return produtoService.getById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseProdutoDto> update(@PathVariable("id") Integer id, @RequestBody RequestProdutoDto body){
		return produtoService.update(id, body);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Integer id){
		return produtoService.delete(id);
	}
}
