package com.projecttattoo.BrenoLendaTattoo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projecttattoo.BrenoLendaTattoo.models.Orcamento;

public interface OrcamentoRespository extends JpaRepository<Orcamento, Integer>{
	List<Orcamento> findByClienteId(Integer id);
}
