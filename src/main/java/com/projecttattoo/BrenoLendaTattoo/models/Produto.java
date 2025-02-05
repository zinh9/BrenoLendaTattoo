package com.projecttattoo.BrenoLendaTattoo.models;

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
@Table(name = "produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, name = "imagem")
	private String imagem;
	
	@Column(nullable = false, name = "nome")
	private String nome;
	
	@Column(nullable = false, name = "largura")
	private Double largura;
	
	@Column(nullable = false, name = "altura")
	private Double altura;
	
	private String descricao;
	
	@Column(nullable = false, name = "valor")
	private Double valor;
}
