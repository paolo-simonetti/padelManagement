package it.solving.padelmanagement.dto.message.insert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class NoticeInsertMessageDTO {

	@NotBlank
	private String message;
	
	@Positive
	private String clubId;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public NoticeInsertMessageDTO(@NotBlank String message, @Positive String clubId) {
		super();
		this.message = message;
		this.clubId = clubId;
	}

	public NoticeInsertMessageDTO() {
		super();
	}
	
	
	
}
