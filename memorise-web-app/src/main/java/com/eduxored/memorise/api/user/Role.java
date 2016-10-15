package com.eduxored.memorise.api.user;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
	USER("ROLE_USER"),
	PUBLISHER("ROLE_PUBLISHER");

	private String authority;

	Role(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}
}
