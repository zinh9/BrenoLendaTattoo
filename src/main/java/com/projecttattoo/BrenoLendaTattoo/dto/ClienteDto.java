package com.projecttattoo.BrenoLendaTattoo.dto;

import java.time.LocalDate;

public record ClienteDto(Integer id, String nome, String sobrenome, String email, String telefone, String cpf, LocalDate dataNascimento) {

}
