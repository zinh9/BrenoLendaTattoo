package com.projecttattoo.BrenoLendaTattoo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.RequestAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.ResponseAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.interfaces.AgendamentoInterfaceService;
import com.projecttattoo.BrenoLendaTattoo.models.Agendamento;
import com.projecttattoo.BrenoLendaTattoo.repositories.AgendamentoRepository;

@Service
public class AgendamentoService implements AgendamentoInterfaceService{
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;

	@Override
	public ResponseEntity<ResponseAgendamentoDto> register(RequestAgendamentoDto body) {
		Agendamento agendamento = new Agendamento();
		
		agendamento.setCliente(body.cliente());
		agendamento.setProduto(body.produto());
		agendamento.setDataAgendamento(body.dataAgendamento());
		agendamento.setStatus(body.status());
		
		agendamentoRepository.save(agendamento);
		
		ResponseAgendamentoDto agendamentoDto = new ResponseAgendamentoDto(
				agendamento.getId(),
				agendamento.getCliente(),
				agendamento.getProduto(),
				agendamento.getDataAgendamento(),
				agendamento.getStatus());
		
		return ResponseEntity.ok(agendamentoDto);
	}

	@Override
	public ResponseEntity<ResponseAgendamentoDto> getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseAgendamentoDto> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseAgendamentoDto> update(Integer id, RequestAgendamentoDto body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
