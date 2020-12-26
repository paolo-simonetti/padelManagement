package it.solving.padelmanagement.dto;

public class ResultDTO {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResultDTO(String message) {
		super();
		this.message = message;
	}

	public ResultDTO() {
		super();
	}
	
	
}
