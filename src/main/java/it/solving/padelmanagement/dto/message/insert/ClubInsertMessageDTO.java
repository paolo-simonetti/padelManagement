package it.solving.padelmanagement.dto.message.insert;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class ClubInsertMessageDTO {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String city;
	
	@NotEmpty
	private Set<Byte> logo=new HashSet<>();

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

	public Set<Byte> getLogo() {
		return logo;
	}

	public void setLogo(Set<Byte> logo) {
		this.logo = logo;
	}

	public ClubInsertMessageDTO(@NotBlank String name, @NotBlank String city, @NotEmpty Set<Byte> logo) {
		super();
		this.name = name;
		this.city = city;
		this.logo = logo;
	}

	public ClubInsertMessageDTO() {
		super();
	}
	
	
	
}
