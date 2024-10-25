package com.projecttattoo.BrenoLendaTattoo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projecttattoo.BrenoLendaTattoo.models.Logins;

public interface LoginsRepository extends JpaRepository<Logins, Long>{
	public Logins findByEmail(String email);
	public Logins findByVerificationCode(String verificationCode);
}