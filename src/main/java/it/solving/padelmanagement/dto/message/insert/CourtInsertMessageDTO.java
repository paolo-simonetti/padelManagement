package it.solving.padelmanagement.dto.message.insert;

import javax.validation.constraints.NotBlank;

public class CourtInsertMessageDTO {

	@NotBlank
	private String name;
	
	private static final String mayBeReserved="true";
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static String getMayBeReserved() {
		return mayBeReserved;
	}

	public CourtInsertMessageDTO(@NotBlank String name) {
		super();
		this.name = name;
	}

	public CourtInsertMessageDTO() {
		super();
	}
	
}
