package com.projecttattoo.BrenoLendaTattoo.dto.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.projecttattoo.BrenoLendaTattoo.models.Cliente;
import com.projecttattoo.BrenoLendaTattoo.models.Orcamento;
import com.projecttattoo.BrenoLendaTattoo.models.Produto;

public record RequestAgendamentoDto(@DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataAgendamento, LocalTime horario, Integer id_orcamento) {

}
