package it.solving.padelmanagement.dto.message.insert;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

public class JoinProposalInsertMessageDTO {

	@Min(1)
	@Max(7)
	private String userLevel;
	
	private String proposalStatus;

	@Positive
	private String applicantId;
	
	@Positive
	private String clubId;

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

	public JoinProposalInsertMessageDTO(@Min(1) @Max(7) String userLevel, String proposalStatus,
			@Positive String applicantId, @Positive String clubId) {
		super();
		this.userLevel = userLevel;
		this.proposalStatus = proposalStatus;
		this.applicantId = applicantId;
		this.clubId = clubId;
	}

	public JoinProposalInsertMessageDTO() {
		super();
	}
	
}
