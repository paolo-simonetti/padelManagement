package it.solving.padelmanagement.dto;

public class NoticeDTO {

	private String id;
	
	private String message;
	
	private String creationDate;
	
	private String clubId;

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

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public NoticeDTO(String id, String message, String creationDate, String clubId) {
		super();
		this.id = id;
		this.message = message;
		this.creationDate = creationDate;
		this.clubId = clubId;
	}

	public NoticeDTO() {
		super();
	}
	
	
	
}
