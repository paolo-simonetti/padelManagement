package it.solving.padelmanagement.dto.message.update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CourtUpdateMessageDTO {

	@NotBlank
	@Positive
	private String id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	@Positive
	private String clubId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public CourtUpdateMessageDTO(@NotBlank @Positive String id, @NotBlank String name,
			@NotBlank @Positive String clubId) {
		super();
		this.id = id;
		this.name = name;
		this.clubId = clubId;
	}

	public CourtUpdateMessageDTO() {
		super();
	}
	
	
}
