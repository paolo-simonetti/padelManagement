package it.solving.padelmanagement.model;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class NewClubProposal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String city;
	
	private ProposalStatus proposalStatus;
	
	@Lob
	private Blob logo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User creator;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Blob getLogo() {
		return logo;
	}

	public void setLogo(Blob logo) {
		this.logo = logo;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public ProposalStatus getProposalStatus() {
		return proposalStatus;
	}

	public void setProposalStatus(ProposalStatus proposalStatus) {
		this.proposalStatus = proposalStatus;
	}

	public NewClubProposal(Long id, String name, String city, Blob logo, ProposalStatus proposalStatus) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.logo = logo;
		this.proposalStatus = proposalStatus;
	}

	public NewClubProposal() {
		super();
	}

	
	
}
