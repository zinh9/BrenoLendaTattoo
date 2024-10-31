package com.projecttattoo.BrenoLendaTattoo.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.projecttattoo.BrenoLendaTattoo.dto.RequestProdutoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.ResponseProdutoDto;

public interface ProdutoInterfaceService {
	public ResponseEntity<ResponseProdutoDto> register(RequestProdutoDto body);
	public ResponseEntity<List<ResponseProdutoDto>> getAll();
	public ResponseEntity<ResponseProdutoDto> getById(Integer id);
	public ResponseEntity<ResponseProdutoDto> update(Integer id, RequestProdutoDto body);
	public ResponseEntity<String> delete(Integer id);
}
