package it.solving.padelmanagement.dto.message.clubmanagement;

import javax.validation.constraints.NotBlank;

public class ClubManagementMessageDTO {

	@NotBlank
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ClubManagementMessageDTO(@NotBlank String date) {
		super();
		this.date = date;
	}

	public ClubManagementMessageDTO() {
		super();
	}

	
}
