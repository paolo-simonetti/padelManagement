package it.solving.padelmanagement.dto.message;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class JwtRequestMessageDTO implements Serializable {
		
	private static final long serialVersionUID = 5471741022367502678L;

	@NotBlank
	private String username;
	
	@NotBlank
	@Size(min=6)
	private String password;

	public JwtRequestMessageDTO() {
		super();
	}

	public JwtRequestMessageDTO(@NotBlank String username, @NotBlank @Size(min = 6) String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
