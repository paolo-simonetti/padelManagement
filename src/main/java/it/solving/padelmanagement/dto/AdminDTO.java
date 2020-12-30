package it.solving.padelmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AdminDTO extends UserDTO {
	
	@JsonIgnore
	private String clubId;

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public AdminDTO() {
		super();
	}
	
	
}
