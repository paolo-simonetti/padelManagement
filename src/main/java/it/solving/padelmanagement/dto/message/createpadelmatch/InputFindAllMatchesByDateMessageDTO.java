package it.solving.padelmanagement.dto.message.createpadelmatch;

import javax.validation.constraints.NotBlank;

public class InputFindAllMatchesByDateMessageDTO {
	
	@NotBlank
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public InputFindAllMatchesByDateMessageDTO(String date) {
		super();
		this.date = date;
	}

	public InputFindAllMatchesByDateMessageDTO() {
		super();
	}
	
	
}
