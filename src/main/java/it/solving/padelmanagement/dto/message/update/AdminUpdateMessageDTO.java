package it.solving.padelmanagement.dto.message.update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

public class AdminUpdateMessageDTO extends UserUpdateMessageDTO {

	private String clubId;

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public AdminUpdateMessageDTO() {
		super();
	}

	public AdminUpdateMessageDTO(@NotBlank String id, @NotBlank String name, @NotBlank String surname,
			@NotBlank @Past String dateOfBirth, @NotBlank String mailAddress, @NotBlank String mobile, String clubId) {
		super(id, name, surname, dateOfBirth, mailAddress, mobile);
		this.clubId=clubId;
	}
	
	
	
}
