package com.projecttattoo.BrenoLendaTattoo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projecttattoo.BrenoLendaTattoo.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	public Cliente findById(int id);
	public Cliente findByEmail(String email);
	public boolean existsByEmail(String email);
}
