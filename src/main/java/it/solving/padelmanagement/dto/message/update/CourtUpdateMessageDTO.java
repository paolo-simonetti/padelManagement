package it.solving.padelmanagement.dto.message.update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CourtUpdateMessageDTO {

	@NotBlank
	@Positive
	private String id;
	
	@NotBlank
	private String name;
	
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

	public CourtUpdateMessageDTO(@NotBlank @Positive String id, @NotBlank String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public CourtUpdateMessageDTO() {
		super();
	}
	
	
}
