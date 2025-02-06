package com.projecttattoo.BrenoLendaTattoo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.multipart.MultipartFile;

import com.projecttattoo.BrenoLendaTattoo.dto.produto.RequestProdutoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.produto.ResponseProdutoDto;
import com.projecttattoo.BrenoLendaTattoo.services.ProdutoService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin-novo-produto")
	public String addNovoProduto(Model model) {
		model.addAttribute("produto", new ResponseProdutoDto(null, null, null, null, null, null, null));
		return "admin_novo_produto";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/register")
	public String register(
			@RequestParam("imagem") MultipartFile imagem,
			@RequestParam("nome") String nome,
	        @RequestParam("largura") Double largura,
	        @RequestParam("altura") Double altura,
	        @RequestParam("descricao") String descricao,
	        @RequestParam("valor") Double valor,
	        Model model
			){
		System.out.println(largura);
		RequestProdutoDto requestProdutoDto = new RequestProdutoDto(imagem, nome, largura, altura, descricao, valor);
		ResponseEntity<ResponseProdutoDto> response = produtoService.register(requestProdutoDto);
		
		if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Produto salvo com sucesso!");
        } else {
            model.addAttribute("error", "Erro ao salvar o produto.");
        }
        return "redirect:/produto/admin-catalogo";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin-catalogo")
	public String listarProdutosAdmin(Model model) {
		ResponseEntity<List<ResponseProdutoDto>> response = produtoService.getAll();
		if(response.getStatusCode().is2xxSuccessful()) {
			model.addAttribute("produtos", response.getBody());			
		}
		return "admin_catalogo";
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/catalogo")
	public String listarProdutos(Model model){
		ResponseEntity<List<ResponseProdutoDto>> response = produtoService.getAll();
		if(response.getStatusCode().is2xxSuccessful()) {
			model.addAttribute("produtos", response.getBody());			
		}
		return "catalogo";
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseProdutoDto> getById(@PathVariable("id") Integer id){
		return produtoService.getById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseProdutoDto> update(@PathVariable("id") Integer id, @RequestBody RequestProdutoDto body){
		return produtoService.update(id, body);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{id}/deletar")
	public String delete(@PathVariable("id") Integer id, Model model){
		ResponseEntity<String> response = produtoService.delete(id);
		if(response.getStatusCode().is2xxSuccessful()) {
			model.addAttribute("produto", "Produto excluido com sucesso!");
		} 
		
		return "redirect:/produto/admin-catalogo";
	}
}
