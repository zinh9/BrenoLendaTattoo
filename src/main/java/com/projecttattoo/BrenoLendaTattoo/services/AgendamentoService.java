package com.projecttattoo.BrenoLendaTattoo.services;

import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.RequestAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.agendamento.ResponseAgendamentoDto;
import com.projecttattoo.BrenoLendaTattoo.interfaces.AgendamentoInterfaceService;
import com.projecttattoo.BrenoLendaTattoo.models.Agendamento;
import com.projecttattoo.BrenoLendaTattoo.models.Cliente;
import com.projecttattoo.BrenoLendaTattoo.models.Orcamento;
import com.projecttattoo.BrenoLendaTattoo.repositories.AgendamentoRepository;
import com.projecttattoo.BrenoLendaTattoo.repositories.ClienteRepository;
import com.projecttattoo.BrenoLendaTattoo.repositories.OrcamentoRespository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService implements AgendamentoInterfaceService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private OrcamentoRespository orcamentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public ResponseEntity<ResponseAgendamentoDto> register(RequestAgendamentoDto body) {
        Optional<Orcamento> orcamentoOpt = orcamentoRepository.findById(body.id_orcamento());
        if (orcamentoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
        Orcamento orcamento = orcamentoOpt.get();
        Agendamento agendamento = new Agendamento();
        agendamento.setDataAgendamento(body.dataAgendamento());
        agendamento.setHorario(body.horario());
        agendamento.setOrcamento(orcamento);
        orcamento.setStatusOrcamento("Agendado!");
 
        orcamentoRepository.save(orcamento);
        agendamentoRepository.save(agendamento);

        ResponseAgendamentoDto agendamentoDto = new ResponseAgendamentoDto(
                agendamento.getId(),
                agendamento.getDataAgendamento(),
                agendamento.getHorario(),
                agendamento.getOrcamento().getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoDto);
    }

    @Override
    public ResponseEntity<ResponseAgendamentoDto> getById(Integer id) {
        Optional<Agendamento> agendamentoOpt = agendamentoRepository.findById(id);

        if (agendamentoOpt.isPresent()) {
            Agendamento agendamento = agendamentoOpt.get();
            ResponseAgendamentoDto agendamentoDto = new ResponseAgendamentoDto(
                    agendamento.getId(),
                    agendamento.getDataAgendamento(),
                    agendamento.getHorario(),
                    agendamento.getOrcamento().getId()
            );
            return ResponseEntity.ok(agendamentoDto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Override
    public ResponseEntity<List<ResponseAgendamentoDto>> getAll() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();

        if (!agendamentos.isEmpty()) {
            List<ResponseAgendamentoDto> agendamentoDtos = agendamentos.stream()
                    .map(agendamento -> new ResponseAgendamentoDto(
                            agendamento.getId(),
                            agendamento.getDataAgendamento(),
                            agendamento.getHorario(),
                            agendamento.getOrcamento().getId()
                    ))
                    .toList();
            return ResponseEntity.ok(agendamentoDtos);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Override
    public ResponseEntity<ResponseAgendamentoDto> update(Integer id, RequestAgendamentoDto body) {
        Optional<Agendamento> agendamentoOpt = agendamentoRepository.findById(id);

        if (agendamentoOpt.isPresent()) {
            Agendamento agendamento = agendamentoOpt.get();

            // Atualiza os campos
            agendamento.setDataAgendamento(body.dataAgendamento());
            agendamento.setHorario(body.horario());

            agendamentoRepository.save(agendamento);

            ResponseAgendamentoDto agendamentoDto = new ResponseAgendamentoDto(
                    agendamento.getId(),
                    agendamento.getDataAgendamento(),
                    agendamento.getHorario(),
                    agendamento.getOrcamento().getId()
            );

            return ResponseEntity.ok(agendamentoDto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Override
    public ResponseEntity<String> delete(Integer id) {
        if (agendamentoRepository.existsById(id)) {
            agendamentoRepository.deleteById(id);
            return ResponseEntity.ok("Agendamento excluído com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agendamento não encontrado.");
    }
}