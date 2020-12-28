package it.solving.padelmanagement.dto.message.clubmanagement;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ClubManagementMessageDTO {

	@NotBlank
	private String date;
	
	@Positive
	private String clubId;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public ClubManagementMessageDTO(@NotBlank String date, @Positive String clubId) {
		super();
		this.date = date;
		this.clubId = clubId;
	}

	public ClubManagementMessageDTO() {
		super();
	}

	
}
