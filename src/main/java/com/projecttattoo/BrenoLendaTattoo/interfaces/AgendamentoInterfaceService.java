package com.projecttattoo.BrenoLendaTattoo.interfaces;

import org.springframework.http.ResponseEntity;

import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.RequestAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.ResponseAgendamentoDto;

public interface AgendamentoInterfaceService {
	public ResponseEntity<ResponseAgendamentoDto> register(RequestAgendamentoDto body);
	public ResponseEntity<ResponseAgendamentoDto> getById(Integer id);
	public ResponseEntity<ResponseAgendamentoDto> getAll();
	public ResponseEntity<ResponseAgendamentoDto> update(Integer id, RequestAgendamentoDto body);
	public ResponseEntity<String> delete(Integer id);
}
