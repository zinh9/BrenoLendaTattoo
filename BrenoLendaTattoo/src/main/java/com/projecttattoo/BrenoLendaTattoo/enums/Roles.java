package com.projecttattoo.BrenoLendaTattoo.enums;

import lombok.Getter;

@Getter
public enum Roles {
	ADMIN("admin"), USER("user");

	private String role;

	private Roles(String role) {
		this.role = role;
	}

}
