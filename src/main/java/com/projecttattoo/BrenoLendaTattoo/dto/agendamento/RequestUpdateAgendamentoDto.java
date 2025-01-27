package com.projecttattoo.BrenoLendaTattoo.dto.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;

import com.projecttattoo.BrenoLendaTattoo.models.Produto;

public record RequestUpdateAgendamentoDto(Produto produto, LocalDate dataAgendamento, LocalTime horario) {

}
