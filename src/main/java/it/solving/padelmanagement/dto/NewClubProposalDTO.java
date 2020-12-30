package it.solving.padelmanagement.dto;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NewClubProposalDTO {
	
	@JsonIgnore
	private String id;
	
	private String name;
	
	private String city;
	
	private String proposalStatus;
	
	private Blob logo;
	
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

	public Blob getLogo() {
		return logo;
	}

	public void setLogo(Blob logo) {
		this.logo = logo;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public NewClubProposalDTO(String id, String name, String city, String proposalStatus, Blob logo,
			String creatorId) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.proposalStatus = proposalStatus;
		this.logo = logo;
		this.creatorId = creatorId;
	}

	public NewClubProposalDTO() {
		super();
	}
	
	
}
