package it.solving.padelmanagement.dto.message.insert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CourtInsertMessageDTO {

	@NotBlank
	private String name;
	
	@NotBlank
	@Positive
	private String clubId;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public CourtInsertMessageDTO(@NotBlank String name, @NotBlank @Positive String clubId) {
		super();
		this.name = name;
		this.clubId = clubId;
	}

	public CourtInsertMessageDTO() {
		super();
	}
	
}
