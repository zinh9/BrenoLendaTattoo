package com.projecttattoo.BrenoLendaTattoo.dto.cliente;

import java.time.LocalDate;

public record ClienteDto(Integer id, String nomeCompleto, String email, String telefone, String cpf) {
    
}
