package it.solving.padelmanagement.dto.message.insert;

import java.sql.Blob;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class ClubInsertMessageDTO {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String city;
	
	private Blob logo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public Blob getLogo() {
		return logo;
	}

	public void setLogo(Blob logo) {
		this.logo = logo;
	}

	public ClubInsertMessageDTO(@NotBlank String name, @NotBlank String city, @NotEmpty Blob logo) {
		super();
		this.name = name;
		this.city = city;
		this.logo = logo;
	}

	public ClubInsertMessageDTO() {
		super();
	}
	
	
	
}
