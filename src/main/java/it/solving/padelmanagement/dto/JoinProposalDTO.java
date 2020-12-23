package it.solving.padelmanagement.dto;

public class JoinProposalDTO {

	private String id;
	
	private String userLevel;
	
	private String proposalStatus;
	
	private String applicantId;
	
	private String clubId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getProposalStatus() {
		return proposalStatus;
	}

	public void setProposalStatus(String proposalStatus) {
		this.proposalStatus = proposalStatus;
	}

	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public JoinProposalDTO(String id, String userLevel, String proposalStatus, String applicantId, String clubId) {
		super();
		this.id = id;
		this.userLevel = userLevel;
		this.proposalStatus = proposalStatus;
		this.applicantId = applicantId;
		this.clubId = clubId;
	}

	public JoinProposalDTO() {
		super();
	}
	
	
	
}
