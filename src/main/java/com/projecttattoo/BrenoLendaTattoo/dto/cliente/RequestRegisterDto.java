package com.projecttattoo.BrenoLendaTattoo.dto.cliente;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public record RequestRegisterDto(String nomeCompleto, String email, String senha, String telefone, String cpf) {

}
