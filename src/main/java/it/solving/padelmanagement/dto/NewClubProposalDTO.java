package it.solving.padelmanagement.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NewClubProposalDTO {
	
	@JsonIgnore
	private String id;
	
	private String name;
	
	private String city;
	
	private String proposalStatus;
	
	private String logoName;
	
	private MultipartFile logo;
	
	@JsonIgnore
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

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public NewClubProposalDTO(String id, String name, String city, String proposalStatus, String logoName,
			MultipartFile logo, String creatorId) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.proposalStatus = proposalStatus;
		this.logoName = logoName;
		this.logo = logo;
		this.creatorId = creatorId;
	}

	public NewClubProposalDTO() {
		super();
	}
	
	
}
