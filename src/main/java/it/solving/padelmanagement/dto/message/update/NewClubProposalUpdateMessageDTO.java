package it.solving.padelmanagement.dto.message.update;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public class NewClubProposalUpdateMessageDTO {

	@Positive
	private String id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String proposalStatus;
	
	@NotEmpty
	private Set<Byte> logo=new HashSet<>();
	
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

	public Set<Byte> getLogo() {
		return logo;
	}

	public void setLogo(Set<Byte> logo) {
		this.logo = logo;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public NewClubProposalUpdateMessageDTO(@Positive String id, @NotBlank String name, @NotBlank String city,
			@NotBlank String proposalStatus, @NotEmpty Set<Byte> logo, @Positive String creatorId) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.proposalStatus = proposalStatus;
		this.logo = logo;
		this.creatorId = creatorId;
	}

	public NewClubProposalUpdateMessageDTO() {
		super();
	}
	
	
	
}