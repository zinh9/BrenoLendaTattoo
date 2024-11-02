package com.projecttattoo.BrenoLendaTattoo.dto;

import java.time.LocalDate;

public record RequestRegisterDto(String nome, String sobrenome, String email, String senha, String telefone, String cpf, LocalDate dataNascimento) {

}
