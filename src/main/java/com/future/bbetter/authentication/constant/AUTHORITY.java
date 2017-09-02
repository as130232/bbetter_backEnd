package com.future.bbetter.authentication.constant;

public enum AUTHORITY {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");
	
	public final String role;
	private AUTHORITY(String role) {
		this.role = role;
	}
	
}
