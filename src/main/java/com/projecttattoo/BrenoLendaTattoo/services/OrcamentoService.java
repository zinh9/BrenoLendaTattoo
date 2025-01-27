package com.projecttattoo.BrenoLendaTattoo.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.projecttattoo.BrenoLendaTattoo.dto.orcamento.RequestOrcamentoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.orcamento.ResponseOrcamentoDto;
import com.projecttattoo.BrenoLendaTattoo.interfaces.OrcamentoInterfaceService;
import com.projecttattoo.BrenoLendaTattoo.models.Orcamento;
import com.projecttattoo.BrenoLendaTattoo.repositories.OrcamentoRespository;

@Service
public class OrcamentoService implements OrcamentoInterfaceService {

	@Autowired
	private OrcamentoRespository orcamentoRespository;

	@Override
	public ResponseEntity<ResponseOrcamentoDto> register(RequestOrcamentoDto body) {
		Orcamento newOrcamento = new Orcamento();

		newOrcamento.setImagem(body.imagem());
		newOrcamento.setAltura(body.altura());
		newOrcamento.setLargura(body.largura());
		newOrcamento.setDescricao(body.descricao());
		newOrcamento.setParteCorpo(body.parteCorpo());
		newOrcamento.setValor(calculateValue(newOrcamento));
		newOrcamento.setStatusOrcamento("Em análise");

		orcamentoRespository.save(newOrcamento);

		ResponseOrcamentoDto orcamentoDto = new ResponseOrcamentoDto(newOrcamento.getId(), newOrcamento.getImagem(),
				newOrcamento.getAltura(), newOrcamento.getLargura(), newOrcamento.getDescricao(),
				newOrcamento.getParteCorpo(), newOrcamento.getValor(), newOrcamento.getStatusOrcamento());

		return ResponseEntity.ok(orcamentoDto);
	}

	@Override
	public Double calculateValue(Orcamento orcamento) {
		final double valorPorCm = 10;
		double altura = orcamento.getAltura(), largura = orcamento.getLargura();

		Map<String, Double> fatorPorParteCorpo = Map.of("braco", 0.1, "costela", 0.15, "antebraco", 0.13, "perna", 0.15,
				"costas", 0.2, "peito", 0.12, "pescoco", 0.22, "pe", 0.13, "outro", 0.14);

		double fatorLocalizacao = fatorPorParteCorpo.getOrDefault(orcamento.getParteCorpo(), 1.0);
		double valorFinal = altura * largura * valorPorCm * fatorLocalizacao;

		return valorFinal;
	}

	@Override
	public ResponseEntity<ResponseOrcamentoDto> getById(Integer id) {
		Optional<Orcamento> orcamentoOpt = orcamentoRespository.findById(id);

		if (orcamentoOpt.isPresent()) {
			Orcamento orcamento = orcamentoOpt.get();
			ResponseOrcamentoDto orcamentoDto = new ResponseOrcamentoDto(orcamento.getId(), orcamento.getImagem(),
					orcamento.getAltura(), orcamento.getLargura(), orcamento.getDescricao(), orcamento.getParteCorpo(),
					orcamento.getValor(), orcamento.getStatusOrcamento());

			return ResponseEntity.ok(orcamentoDto);
		}

		return ResponseEntity.badRequest().build();
	}

	@Override
	public ResponseEntity<List<ResponseOrcamentoDto>> getAll() {
		List<Orcamento> orcamentos = orcamentoRespository.findAll();

		if (!orcamentos.isEmpty()) {
			List<ResponseOrcamentoDto> orcamentosDto = orcamentos.stream()
					.map(orcamento -> new ResponseOrcamentoDto(orcamento.getId(), orcamento.getImagem(),
							orcamento.getAltura(), orcamento.getLargura(), orcamento.getDescricao(),
							orcamento.getParteCorpo(), orcamento.getValor(), orcamento.getStatusOrcamento()))
					.toList();
			
			return ResponseEntity.ok(orcamentosDto);
		}
		
		return ResponseEntity.badRequest().build();
	}

	@Override
	public ResponseEntity<ResponseOrcamentoDto> update(Integer id, RequestOrcamentoDto body) {
		Optional<Orcamento> orcamentoOpt = orcamentoRespository.findById(id);
		
		if(orcamentoOpt.isPresent()) {
			Orcamento orcamento = orcamentoOpt.get();
			
			orcamento.setImagem(body.imagem());
			orcamento.setAltura(body.altura());
			orcamento.setLargura(body.largura());
			orcamento.setDescricao(body.descricao());
			orcamento.setParteCorpo(body.parteCorpo());
			orcamento.setValor(calculateValue(orcamento));
			orcamento.setStatusOrcamento("Em análise");
			
			orcamentoRespository.save(orcamento);
			
			ResponseOrcamentoDto orcamentoDto = new ResponseOrcamentoDto(orcamento.getId(), orcamento.getImagem(),
					orcamento.getAltura(), orcamento.getLargura(), orcamento.getDescricao(), orcamento.getParteCorpo(),
					orcamento.getValor(), orcamento.getStatusOrcamento());
			
			return ResponseEntity.ok(orcamentoDto);
		}
		
		return ResponseEntity.badRequest().build();
	}

	@Override
	public ResponseEntity<String> update(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseOrcamentoDto> updateStatus(Integer id, String newStatus) {
		Optional<Orcamento> orcamentoOpt = orcamentoRespository.findById(id);
		
		if(orcamentoOpt.isPresent()) {
			Orcamento orcamento = orcamentoOpt.get();
			
			if(!List.of("Concluído", "Cancelado").contains(newStatus)) {
				return ResponseEntity.badRequest().build();
			}
			
			orcamento.setStatusOrcamento(newStatus);
			orcamentoRespository.save(orcamento);
			
			ResponseOrcamentoDto orcamentoDto = new ResponseOrcamentoDto(orcamento.getId(), orcamento.getImagem(),
	                orcamento.getAltura(), orcamento.getLargura(), orcamento.getDescricao(), orcamento.getParteCorpo(),
	                orcamento.getValor(), orcamento.getStatusOrcamento());
			
			return ResponseEntity.ok(orcamentoDto);
		}
		
		return ResponseEntity.notFound().build();
	}

}
