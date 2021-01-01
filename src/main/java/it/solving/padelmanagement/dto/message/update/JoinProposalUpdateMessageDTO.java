package it.solving.padelmanagement.dto.message.update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class JoinProposalUpdateMessageDTO {

	@Positive
	private String id;
	
	@Min(1)
	@Max(7)
	private String userLevel;
	
	@NotBlank
	private String proposalStatus;

	@Positive
	private String applicantId;
	
	@Positive
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

	public JoinProposalUpdateMessageDTO(@Positive String id, @Min(1) @Max(7) String userLevel,
			@NotBlank String proposalStatus, @Positive String applicantId, @Positive String clubId) {
		super();
		this.id = id;
		this.userLevel = userLevel;
		this.proposalStatus = proposalStatus;
		this.applicantId = applicantId;
		this.clubId = clubId;
	}

	public JoinProposalUpdateMessageDTO() {
		super();
	}
	
	
}
