package it.solving.padelmanagement.dto.message.update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.web.multipart.MultipartFile;

public class NewClubProposalUpdateMessageDTO {

	@Positive
	private String id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String proposalStatus;
	
	private String logoName;
		
	private MultipartFile logo;

	
	@Positive
	private String creatorId;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProposalStatus() {
		return proposalStatus;
	}

	public void setProposalStatus(String proposalStatus) {
		this.proposalStatus = proposalStatus;
	}

	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
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

	public NewClubProposalUpdateMessageDTO(@Positive String id, @NotBlank String name, @NotBlank String city,
			@NotBlank String proposalStatus, String logoName, MultipartFile logo,
			@Positive String creatorId) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.proposalStatus = proposalStatus;
		this.logoName = logoName;
		this.logo = logo;
		this.creatorId = creatorId;
	}

	public NewClubProposalUpdateMessageDTO() {
		super();
	}
	
	
	
}
