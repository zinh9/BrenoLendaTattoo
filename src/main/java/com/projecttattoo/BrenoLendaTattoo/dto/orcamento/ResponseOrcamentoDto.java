package com.projecttattoo.BrenoLendaTattoo.dto.orcamento;

import com.projecttattoo.BrenoLendaTattoo.models.Agendamento;

public record ResponseOrcamentoDto(Integer id, String imagem, Double altura, Double largura, String descricao, String parteCorpo, Double valor, String statusOrcamento, Agendamento agendamento) {

}

// tentar fazer encontrar o id do orcamento para adicionar um agendamento