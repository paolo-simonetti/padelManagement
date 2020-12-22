package it.solving.padelmanagement.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class JoinProposal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer userLevel;
	
	private ProposalStatus proposalStatus;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User applicant;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Club club;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getUserLevel() {
		return userLevel;
	}


	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}


	public User getApplicant() {
		return applicant;
	}


	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}


	public Club getClub() {
		return club;
	}


	public void setClub(Club club) {
		this.club = club;
	}


	public ProposalStatus getProposalStatus() {
		return proposalStatus;
	}


	public void setProposalStatus(ProposalStatus proposalStatus) {
		this.proposalStatus = proposalStatus;
	}


	public JoinProposal(Long id, Integer userLevel, ProposalStatus proposalStatus) {
		super();
		this.id = id;
		this.userLevel = userLevel;
		this.proposalStatus = proposalStatus;
	}


	public JoinProposal() {
		super();
	}
	
}
