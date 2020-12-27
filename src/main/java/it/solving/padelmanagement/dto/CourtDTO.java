package it.solving.padelmanagement.dto;

public class CourtDTO {

	private String id;
	
	private String name;
	
	private String clubId;
	
	private String mayBeReserved;

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
	
	public String getMayBeReserved() {
		return mayBeReserved;
	}

	public void setMayBeReserved(String mayBeReserved) {
		this.mayBeReserved = mayBeReserved;
	}

	public CourtDTO(String id, String name, String clubId) {
		super();
		this.id = id;
		this.name = name;
		this.clubId = clubId;
	}

	public CourtDTO() {
		super();
	}
	
	
}
