package it.solving.padelmanagement.dto;

public class AdminDTO extends UserDTO {

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
