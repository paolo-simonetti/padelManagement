package it.solving.padelmanagement.model;

import java.util.HashMap;
import java.util.Map;

public enum ProposalStatus {

	PENDING("pending"),
	APPROVED("approved"),
	REJECTED("rejected");
	
	private String stringProposalStatus;

	public String getStringProposalStatus() {
		return stringProposalStatus;
	}

	public void setStringProposalStatus(String stringProposalStatus) {
		this.stringProposalStatus = stringProposalStatus;
	}

	ProposalStatus(String stringProposalStatus) {
		this.stringProposalStatus = stringProposalStatus;
	}
	
	public static Map<String,ProposalStatus> conversionProposalStatus=new HashMap<>();
	
	static {
		conversionProposalStatus.put("pending",PENDING);
		conversionProposalStatus.put("approved",APPROVED);
		conversionProposalStatus.put("rejected",REJECTED);
	}
}
