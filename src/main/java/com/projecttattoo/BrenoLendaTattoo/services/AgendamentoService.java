package com.projecttattoo.BrenoLendaTattoo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.RequestAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.RequestUpdateAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.ResponseAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.interfaces.AgendamentoInterfaceService;
import com.projecttattoo.BrenoLendaTattoo.models.Agendamento;
import com.projecttattoo.BrenoLendaTattoo.repositories.AgendamentoRepository;

@Service
public class AgendamentoService implements AgendamentoInterfaceService {

	@Autowired
	private AgendamentoRepository agendamentoRepository;

	@Override
	public ResponseEntity<ResponseAgendamentoDto> register(RequestAgendamentoDto body) {
		Agendamento agendamento = new Agendamento();

		agendamento.setCliente(body.cliente());
		agendamento.setProduto(body.produto());
		agendamento.setDataAgendamento(body.dataAgendamento());
		agendamento.setStatus(body.status());
		agendamento.setHorario(body.horario());

		agendamentoRepository.save(agendamento);

		ResponseAgendamentoDto agendamentoDto = new ResponseAgendamentoDto(agendamento.getId(),
				agendamento.getCliente(), agendamento.getProduto(), agendamento.getDataAgendamento(),
				agendamento.getStatus(), agendamento.getHorario());

		return ResponseEntity.ok(agendamentoDto);
	}

	@Override
	public ResponseEntity<ResponseAgendamentoDto> getById(Integer id) {
		Optional<Agendamento> agendamentoOpt = agendamentoRepository.findById(id);
		
		if(agendamentoOpt.isPresent()) {
			Agendamento agendamento = agendamentoOpt.get();
			
			ResponseAgendamentoDto agendamentoDto = new ResponseAgendamentoDto(agendamento.getId()
					, agendamento.getCliente(), agendamento.getProduto(), agendamento.getDataAgendamento()
					, agendamento.getStatus(), agendamento.getHorario());
			
			return ResponseEntity.ok(agendamentoDto);
		}
		
		return ResponseEntity.badRequest().build();
	}

	@Override
	public ResponseEntity<List<ResponseAgendamentoDto>> getAll() {
		List<Agendamento> agendamentos = agendamentoRepository.findAll();
		
		if(!agendamentos.isEmpty()) {
			List<ResponseAgendamentoDto> agendamentoDtos = agendamentos.stream()
					.map(agendamento -> new ResponseAgendamentoDto(agendamento.getId(), agendamento.getCliente()
							, agendamento.getProduto(), agendamento.getDataAgendamento()
							, agendamento.getStatus(), agendamento.getHorario()))
					.toList();
			
			return ResponseEntity.ok(agendamentoDtos);
		}
		
		return ResponseEntity.badRequest().build();
	}

	@Override
	public ResponseEntity<ResponseAgendamentoDto> update(Integer id, RequestUpdateAgendamentoDto body) {
		Optional<Agendamento> agendamentoOpt = agendamentoRepository.findById(id);

		if (agendamentoOpt.isPresent()) {
			Agendamento agendamento = agendamentoOpt.get();

			agendamento.setProduto(body.produto());
			agendamento.setDataAgendamento(body.dataAgendamento());
			agendamento.setHorario(body.horario());

			agendamentoRepository.save(agendamento);

			ResponseAgendamentoDto agendamentoDto = new ResponseAgendamentoDto(agendamento.getId(),
					agendamento.getCliente(), agendamento.getProduto(), agendamento.getDataAgendamento(),
					agendamento.getStatus(), agendamento.getHorario());
			
			return ResponseEntity.ok(agendamentoDto);
		}
		
		return ResponseEntity.badRequest().build();
	}

	@Override
	public ResponseEntity<String> delete(Integer id) {
		if (agendamentoRepository.existsById(id)) {
			agendamentoRepository.deleteById(id);

			return ResponseEntity.ok("Agendamento excluido com sucesso!");
		}

		return ResponseEntity.badRequest().build();
	}

}
