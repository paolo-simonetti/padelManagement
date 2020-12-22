package it.solving.padelmanagement.model;

import java.util.HashMap;
import java.util.Map;

public enum Role {

	ROLE_SUPER_ADMIN("superAdmin"),
	ROLE_ADMIN("admin"),
	ROLE_PLAYER("player"),
	ROLE_GUEST("guest");
	
	private String stringRole;

	public String getStringRole() {
		return stringRole;
	}

	public void setStringRole(String stringRole) {
		this.stringRole = stringRole;
	}

	private Role(String stringRole) {
		this.stringRole = stringRole;
	}
	
	public static Map<String,Role> conversionRole = new HashMap<>();
	
	static {
		conversionRole.put("superAdmin",ROLE_SUPER_ADMIN);
		conversionRole.put("admin",ROLE_ADMIN);
		conversionRole.put("player",ROLE_PLAYER);
		conversionRole.put("guest",ROLE_GUEST);
	}
	
	
}
