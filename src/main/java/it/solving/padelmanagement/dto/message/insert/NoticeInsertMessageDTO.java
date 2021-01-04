package it.solving.padelmanagement.dto.message.insert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class NoticeInsertMessageDTO {

	@NotBlank
	private String message;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public NoticeInsertMessageDTO(@NotBlank String message, @Positive String clubId) {
		super();
		this.message = message;
	}

	public NoticeInsertMessageDTO() {
		super();
	}
	
	
	
}
