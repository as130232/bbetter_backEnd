package com.future.bbetter.authentication.constant;

public enum AuthorityItem {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");
	
	private final String role;
	private AuthorityItem(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
}
