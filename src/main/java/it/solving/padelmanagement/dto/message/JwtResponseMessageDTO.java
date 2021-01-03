package it.solving.padelmanagement.dto.message;

import java.io.Serializable;

public class JwtResponseMessageDTO implements Serializable {
	
	private static final long serialVersionUID = -1018187564845264657L;
	
	private final String jwtToken;

	public String getJwtToken() {
		return jwtToken;
	}

	public JwtResponseMessageDTO(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}
	
}
