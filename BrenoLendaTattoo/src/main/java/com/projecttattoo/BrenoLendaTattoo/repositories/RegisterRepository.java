package com.projecttattoo.BrenoLendaTattoo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projecttattoo.BrenoLendaTattoo.models.Cliente;

public interface RegisterRepository extends JpaRepository<Cliente, Long>{
	public Cliente findByEmail(String email);
	public Cliente findByCpf(String cpf);
}
