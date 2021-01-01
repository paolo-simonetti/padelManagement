package it.solving.padelmanagement.dto.message.insert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class ClubInsertMessageDTO {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String logoName;
		
	@NotNull
	private MultipartFile logo;

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
	
	public MultipartFile getLogo() {
		return logo;
	}

	public void setLogo(MultipartFile logo) {
		this.logo = logo;
	}

	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}
	
	public ClubInsertMessageDTO(@NotBlank String name, @NotBlank String city, @NotBlank String logoName,
			@NotNull MultipartFile logo) {
		super();
		this.name = name;
		this.city = city;
		this.logoName = logoName;
		this.logo = logo;
	}

	public ClubInsertMessageDTO() {
		super();
	}
	
	
	
}
