package it.solving.padelmanagement.dto.message.insert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.web.multipart.MultipartFile;

public class NewClubProposalInsertMessageDTO {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String city;
	
	private static final String proposalStatus="pending";
	
	@NotBlank
	private String logoName;
	
	@NotNull
	private MultipartFile logo;
	
	@Positive
	private String creatorId;

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

	public static String getProposalStatus() {
		return proposalStatus;
	}

	public MultipartFile getLogo() {
		return logo;
	}

	public void setLogo(MultipartFile logo) {
		this.logo = logo;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	
	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}
	
	public NewClubProposalInsertMessageDTO(@NotBlank String name, @NotBlank String city, @NotBlank String logoName,
			@NotNull MultipartFile logo, @Positive String creatorId) {
		super();
		this.name = name;
		this.city = city;
		this.logoName = logoName;
		this.logo = logo;
		this.creatorId = creatorId;
	}

	public NewClubProposalInsertMessageDTO() {
		super();
	}
	
}
