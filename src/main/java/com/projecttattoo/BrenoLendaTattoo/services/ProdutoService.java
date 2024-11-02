package com.projecttattoo.BrenoLendaTattoo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projecttattoo.BrenoLendaTattoo.dto.cliente.ClienteDto;
import com.projecttattoo.BrenoLendaTattoo.dto.produto.RequestProdutoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.produto.ResponseProdutoDto;
import com.projecttattoo.BrenoLendaTattoo.interfaces.ProdutoInterfaceService;
import com.projecttattoo.BrenoLendaTattoo.models.Produto;
import com.projecttattoo.BrenoLendaTattoo.repositories.ProdutoRepository;

@Service
public class ProdutoService implements ProdutoInterfaceService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public ResponseEntity<ResponseProdutoDto> register(RequestProdutoDto body) {
		Produto newProduto = new Produto();

		newProduto.setNome(body.nome());
		newProduto.setDescricao(body.descricao());
		newProduto.setPreco(body.preco());

		produtoRepository.save(newProduto);

		ResponseProdutoDto produtoDto = new ResponseProdutoDto(newProduto.getId(), newProduto.getNome(),
				newProduto.getDescricao(), newProduto.getPreco());

		return ResponseEntity.ok(produtoDto);
	}

	@Override
	public ResponseEntity<List<ResponseProdutoDto>> getAll() {
		List<Produto> produtos = produtoRepository.findAll();

		if(!produtos.isEmpty()) {
			List<ResponseProdutoDto> produtosDtos = produtos.stream().map(produto -> new ResponseProdutoDto(produto.getId(),
					produto.getNome(), produto.getDescricao(), produto.getPreco())).toList();
			
			return ResponseEntity.ok(produtosDtos);
		}
		
		return ResponseEntity.badRequest().build();
	}

	@Override
	public ResponseEntity<ResponseProdutoDto> getById(Integer id) {
		Optional<Produto> produtoOpt = produtoRepository.findById(id);
		
		if(produtoOpt.isPresent()) {
			Produto produto = produtoOpt.get();
			ResponseProdutoDto produtoDto = new ResponseProdutoDto(produto.getId(), produto.getNome(),
					produto.getDescricao(), produto.getPreco());

			return ResponseEntity.ok(produtoDto);
		}
		
		return ResponseEntity.badRequest().build();
	}

	@Override
	public ResponseEntity<ResponseProdutoDto> update(Integer id, RequestProdutoDto body) {
		Optional<Produto> produtoOpt = produtoRepository.findById(id);
		
		if(produtoOpt.isPresent()) {
			Produto produto = new Produto();
			
			produto.setNome(body.nome());
			produto.setDescricao(body.descricao());
			produto.setPreco(body.preco());
			
			ResponseProdutoDto produtoDto = new ResponseProdutoDto(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco());
			
			return ResponseEntity.ok(produtoDto);
		}
		
		return ResponseEntity.badRequest().build();
	}

	@Override
	public ResponseEntity<String> delete(Integer id) {
		if(produtoRepository.existsById(id)) {
			produtoRepository.deleteById(id);
			
			return ResponseEntity.ok().body("Produto deletado com sucesso!");
		}
		
		return ResponseEntity.badRequest().body("Não foi possivel deletar o produto!");
	}

}
