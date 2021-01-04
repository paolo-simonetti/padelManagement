package it.solving.padelmanagement.dto.message.update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

public class NoticeUpdateMessageDTO {

	@Positive
	private String id;
	
	@NotBlank
	private String message;
	
	@PastOrPresent
	private String creationDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public NoticeUpdateMessageDTO(@Positive String id, @NotBlank String message, @PastOrPresent String creationDate) {
		super();
		this.id = id;
		this.message = message;
		this.creationDate = creationDate;
	}

	public NoticeUpdateMessageDTO() {
		super();
	}
	
	
}
