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

	public AdminDTO(String id, String name, String surname, String dateOfBirth, String mailAddress, String mobile, 
			String clubId) {
		super(id, name, surname, dateOfBirth, mailAddress, mobile);
		this.clubId=clubId;
	}
	
	
	
}
