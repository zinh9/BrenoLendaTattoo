package com.projecttattoo.BrenoLendaTattoo.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public record RequestRegisterDto(String nome, String sobrenome, String email, String senha, String telefone, String cpf, @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataNascimento) {

}
