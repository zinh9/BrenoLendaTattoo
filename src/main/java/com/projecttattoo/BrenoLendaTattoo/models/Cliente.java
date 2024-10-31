package com.projecttattoo.BrenoLendaTattoo.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 30, nullable = false)
    private String nome;

    @Column(name = "sobrenome", length = 80, nullable = false)
    private String sobrenome;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "telefone", length = 20, nullable = false, unique = true)
    private String telefone;

    @Column(name = "CPF", length = 20, nullable = false, unique = true)
    private String cpf;
    
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;
}
